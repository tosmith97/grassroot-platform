package za.org.grassroot.core.repository;

/**
 * Created by luke on 2015/07/16.
 */
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import za.org.grassroot.core.domain.Account;
import za.org.grassroot.core.domain.Group;
import za.org.grassroot.core.domain.PaidGroup;
import za.org.grassroot.core.domain.User;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByAccountName(String accountName);

    List<Account> findByPrimaryEmail(String primaryEmail);

    List<Account> findByEnabled(boolean enabled);

    Account findByAdministrators(User administrator);

    Account findByPaidGroups(PaidGroup paidGroup);

}
