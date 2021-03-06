package za.org.grassroot.services;

import za.org.grassroot.core.domain.*;
import za.org.grassroot.core.dto.GroupDTO;
import za.org.grassroot.core.dto.GroupTreeDTO;
import za.org.grassroot.core.enums.EventType;
import za.org.grassroot.core.enums.GroupDefaultImage;
import za.org.grassroot.services.enums.GroupPermissionTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GroupBroker {

    Group load(String groupUid);

    List<Group> loadAll();

    List<Group> searchUsersGroups(String userUid, String searchTerm);

    Group checkForDuplicate(String userUid, String groupName);

    /** METHODS FOR CREATING AND EDITING GROUPS **/

    Group create(String userUid, String name, String parentGroupUid, Set<MembershipInfo> membershipInfos,
                 GroupPermissionTemplate groupPermissionTemplate, String description, Integer reminderMinutes, boolean openJoinToken);



    void deactivate(String userUid, String groupUid, boolean checkIfWithinTimeWindow);

    boolean isDeactivationAvailable(User user, Group group, boolean checkIfWithinTimeWindow);

    void updateName(String userUid, String groupUid, String name);

    void updateDescription(String userUid, String groupUid, String description);

    void updateGroupDefaultReminderSetting(String userUid, String groupUid, int reminderMinutes);

    void updateGroupDefaultLanguage(String userUid, String groupUid, String newLocale, boolean includeSubGroups);

    /** METHODS FOR DEALING WITH MEMBERS AND PERMISSIONS **/

    void addMembers(String userUid, String groupUid, Set<MembershipInfo> membershipInfos, boolean adminUserCalling);

    void addMemberViaJoinCode(String userUidToAdd, String groupUid, String tokenPassed);

    void notifyOrganizersOfJoinCodeUse(Instant periodStart, Instant periodEnd);

    void removeMembers(String userUid, String groupUid, Set<String> memberUids);

    void unsubscribeMember(String userUid, String groupUid);

    void updateMembershipRole(String userUid, String groupUid, String memberUid, String roleName);

    void updateMembersToRole(String userUid, String groupUid, Set<String> memberUids, String roleName);

    void updateMembers(String userUid, String groupUid, Set<MembershipInfo> membershipInfos);

    void updateGroupPermissions(String userUid, String groupUid, Map<String, Set<Permission>> newPermissions);

    void updateGroupPermissionsForRole(String userUid, String groupUid, String roleName, Set<Permission> permissionsToAdd,
                                       Set<Permission> permissionsToRemove);




    /** METHODS FOR DEALING WITH JOIN TOKENS, PUBLIC SETTINGS, AND SEARCHING **/

    String openJoinToken(String userUid, String groupUid, LocalDateTime expiryDateTime);

    void closeJoinToken(String userUid, String groupUid);

    void updateDiscoverable(String userUid, String groupUid, boolean discoverable, String authUserPhoneNumber);

	/**
     * Core search method. Finds discoverable groups corresponding to the term given, for which the user is not a member.
     * If location filter is null, then no location filtering is performed.
     *
     * @param userUid user searcher
     * @param searchTerm query string
     * @param locationFilter optional, nullable, location filter options
     * @param restrictToGroupName restricts to just the group name, e.g., if want to display separately
     * @return group list
     */
    List<Group> findPublicGroups(String userUid, String searchTerm, GroupLocationFilter locationFilter, boolean restrictToGroupName);

    Group findGroupFromJoinCode(String joinCode);

    /** METHODS FOR DEALING WITH SUBGROUPS, LINKING GROUPS, AND MERGING **/

    Set<Group> subGroups(String groupUid);

    List<Group> parentChain(String groupUid);

    List<GroupTreeDTO> groupTree(String userUid);

    Set<Group> possibleParents(String userUid, String groupUid);

    void link(String userUid, String childGroupUid, String parentGroupUid);

    Set<Group> mergeCandidates(String userUid, String groupUid);

    Group merge(String userUid, String firstGroupUid, String secondGroupUid,
                boolean leaveActive, boolean orderSpecified, boolean createNew, String newGroupName);

    /** METHODS FOR RETRIEVING GROUP HISTORY & GROUP LOGS **/

    LocalDateTime getLastTimeGroupActiveOrModified(String groupUid);

    GroupLog getMostRecentLog(Group group);

    List<LocalDate> getMonthsGroupActive(String groupUid);

    List<GroupLog> getLogsForGroup(Group group, LocalDateTime periodStart, LocalDateTime periodEnd);

    // pass null to eventType to get all events
    // pass null to either of the timestamps to leave unlimited (i.e., all the way to future, or all the way to past
    List<Event> retrieveGroupEvents(Group group, EventType eventType, Instant periodStart, Instant periodEnd);

    List<Group> fetchGroupsWithOneCharNames(User creatingUser, int sizeThreshold);

    List<GroupDTO> fetchUserCreatedGroups(User user);

    void calculateGroupLocation(String groupUid, LocalDate localDate);

    void setGroupImageToDefault(String userUid, String groupUid, GroupDefaultImage defaultImage, boolean removeCustomImage);

    void saveGroupImage(String userUid, String groupUid, String format, byte[] image);

    Group getGroupByImageUrl(String imageUrl); //todo this needs to be in a separate interface

    ChangedSinceData<Group> getActiveGroups(User user, Instant changedSince);
}
