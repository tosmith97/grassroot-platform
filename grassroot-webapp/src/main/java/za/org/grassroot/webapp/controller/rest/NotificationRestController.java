package za.org.grassroot.webapp.controller.rest;

import edu.emory.mathcs.backport.java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.org.grassroot.core.domain.Notification;
import za.org.grassroot.core.domain.User;
import za.org.grassroot.webapp.model.rest.RequestObjects.NotificationDTO;
import za.org.grassroot.integration.services.NotificationService;
import za.org.grassroot.services.UserManagementService;
import za.org.grassroot.services.exception.NotificationAlreadyUpdatedException;
import za.org.grassroot.webapp.enums.RestMessage;
import za.org.grassroot.webapp.enums.RestStatus;
import za.org.grassroot.webapp.model.rest.ResponseWrappers.NotificationWrapper;
import za.org.grassroot.webapp.model.rest.ResponseWrappers.ResponseWrapper;
import za.org.grassroot.webapp.model.rest.ResponseWrappers.ResponseWrapperImpl;
import za.org.grassroot.webapp.util.RestUtil;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by paballo on 2016/04/13.
 */
@RestController
@RequestMapping("/api/notification")
public class NotificationRestController {

    private static final Logger log = LoggerFactory.getLogger(NotificationRestController.class);

    private static final int pageLength = 20;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserManagementService userManagementService;


    @RequestMapping(value = "/list/{phoneNumber}/{code}", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper> getNotifications(@PathVariable("phoneNumber") String phoneNumber, @PathVariable("code") String code,
                                                            @RequestParam(value = "page", required = false) Integer page,
                                                            @RequestParam(value = "size", required = false) Integer size) {

        User user = userManagementService.loadOrSaveUser(phoneNumber);
        page = (page == null) ? 0 : page;
        size = (size == null) ? pageLength : size;

        Page<Notification> pageable = notificationService.fetchPagedAndroidNotifications(user, page, size);
        log.info("getNotifications ... user has {} notifications in total", pageable.getTotalElements());

        ResponseEntity<ResponseWrapper> responseWrapper;
        if (page > pageable.getTotalPages()) {
            responseWrapper = RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.NOTIFICATIONS_FINISHED);
        } else {
            List<NotificationDTO> notificationDTOList = pageable.getContent()
                    .stream()
                    .filter(NotificationDTO::isNotificationOfTypeForDTO)
                    .map(NotificationDTO::convertToDto)
                    .collect(Collectors.toList());
            log.info("notificationDTOList size ={}", notificationDTOList.size());
            NotificationWrapper notificationWrapper = new NotificationWrapper(pageable, notificationDTOList);
            responseWrapper = RestUtil.okayResponseWithData(RestMessage.NOTIFICATIONS, notificationWrapper);
        }
        return responseWrapper;
    }

    @RequestMapping(value = "/list/since/{phoneNumber}/{code}", method = RequestMethod.GET)
    public ResponseEntity<ResponseWrapper> getNotificationsSince(@PathVariable String phoneNumber, @PathVariable String code,
                                                                 @RequestParam(value = "createdSince", required = false) Long createdSince) {

        User user = userManagementService.findByInputNumber(phoneNumber);
        Instant intervalStart = createdSince == null ? null : Instant.ofEpochMilli(createdSince);
        List<Notification> notifications = notificationService.fetchAndroidNotificationsSince(user.getUid(), intervalStart);
        List<NotificationDTO> notificationDTOs = notifications
                .stream()
                .filter(NotificationDTO::isNotificationOfTypeForDTO)
                .map(NotificationDTO::convertToDto)
                .collect(Collectors.toList());
        NotificationWrapper wrapper = new NotificationWrapper(notificationDTOs);
        return RestUtil.okayResponseWithData(RestMessage.NOTIFICATIONS, wrapper);

    }

    @RequestMapping(value = "/update/read/{phoneNumber}/{code}", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper> updateReadStatus(@PathVariable("phoneNumber") String phoneNumber, @PathVariable("code") String code,
                                                            @RequestParam("uid") String uid) throws Exception{

        User user = userManagementService.loadOrSaveUser(phoneNumber);
        Notification notification = notificationService.loadNotification(uid);

        log.info("updating notification read status for user uid : {}, notification uid : {}", user.getUid(), uid);
        if (!notification.getTarget().equals(user)) {
            return RestUtil.accessDeniedResponse();
        }

        if (notification.isRead()) {
            log.info("Trying to update notification when already read");
            return RestUtil.errorResponse(HttpStatus.ALREADY_REPORTED, RestMessage.ALREADY_UPDATED);
        } else {
            notificationService.updateNotificationsViewedAndRead(Collections.singleton(uid));
            return RestUtil.messageOkayResponse(RestMessage.NOTIFICATION_UPDATED);
        }
    }

    @RequestMapping(value = "/update/read/batch/{phoneNumber}/{code}", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper> updateViewedStatusBatch(@PathVariable String phoneNumber, @PathVariable String code,
                                                                   @RequestParam boolean read, @RequestParam List<String> notificationUids) {
        try {
            if (notificationUids != null && !notificationUids.isEmpty()) {
                notificationService.updateNotificationsViewedAndRead(new HashSet<>(notificationUids));
                return RestUtil.messageOkayResponse(RestMessage.NOTIFICATION_UPDATED);
            } else {
                return RestUtil.messageOkayResponse(RestMessage.EMPTY_LIST);
            }
        } catch (Exception e) {
            return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.ERROR);
        }
    }

    @ExceptionHandler(NotificationAlreadyUpdatedException.class)
    public ResponseEntity<ResponseWrapper> handleException(){
        ResponseWrapper responseWrapper = new ResponseWrapperImpl(HttpStatus.CONFLICT, RestMessage.ALREADY_UPDATED, RestStatus.FAILURE);
        return  new ResponseEntity<>(responseWrapper, HttpStatus.valueOf(responseWrapper.getCode()));
    }
}
