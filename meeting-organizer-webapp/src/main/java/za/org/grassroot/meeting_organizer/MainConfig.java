package za.org.grassroot.meeting_organizer;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import za.org.grassroot.meeting_organizer.common.GrassRootMeetingOrganiserProfiles;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by luke on 2015/07/23.
 */
@Configuration
@ComponentScan( basePackages = {"za.org.grassroot.meeting_organizer"})
@EntityScan
@EnableJpaRepositories
public class MainConfig {



}
