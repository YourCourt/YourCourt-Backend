package yourcourt.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource		dataSource;

	private String	adminString	= "admin";
	private String	userString	= "user";
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
		
		//Users
		.antMatchers(HttpMethod.GET, "/users").hasAuthority(this.adminString)
		.antMatchers(HttpMethod.GET, "/users/{^[\\d]$}").hasAuthority(this.adminString)
		.antMatchers(HttpMethod.DELETE, "/users/{^[\\d]$}").hasAuthority(this.adminString)
		.antMatchers(HttpMethod.POST, "/users").anonymous()
		.antMatchers(HttpMethod.PUT, "/users/{^[\\d]$}").hasAuthority(this.userString);
		
		httpSecurity.csrf().disable().httpBasic()/*.and().addFilter(jwtAuthorizationFilter())*/;

    }
	
	
	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource).usersByUsernameQuery("select username,password,enabled " + "from logins " + "where username = ?")
			.authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?").passwordEncoder(this.passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		//PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
		//return encoder;
		return new BCryptPasswordEncoder();
	}
}
