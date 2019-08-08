//package ib.project;
//
//import javax.sql.DataSource;
//
//import org.apache.catalina.filters.HttpHeaderSecurityFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//
//@Configuration
//
//public class SecurityConfiguration{
//	
//
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//	@Autowired
//	private DataSource dataSource;
//	
//	
//	@Value("${spring.queries.users-query}")
//	private String usersQuery;
//	
//	
//	@Value("${spring.queries.authorities-query}")
//	private String authoritiesQuery;
//	
//	@Override
//	protected void configure(HttpHeaderSecurityFilter http) throws Exception {
//		auth.jdbcAuthenti
//	}
//}
