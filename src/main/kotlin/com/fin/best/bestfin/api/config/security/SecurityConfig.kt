package com.fin.best.bestfin.api.config.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@EnableWebSecurity
class SecurityConfig(private val bestFinAccountService: BestFinAccountService,
                     private val authenticationEntryPoint: AuthenticationEntryPoint,
                     private val bestFinAccessDeniedHandler: BestFinAccessDeniedHandler) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // ### public ##############################################################################
                .antMatchers("/healthCheck").permitAll()

                // - 가입 및 인증 주소는 누구나 접근가능
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/bestfin/**").permitAll()
                .antMatchers("/api/commons/**").permitAll()


                // 임시 권한 해제
                .antMatchers("/api/v1/**").permitAll()



                //엑셀 다운
                .antMatchers(HttpMethod.GET, "/api/glovv/store/goods/excel/goods").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/store/orders/excel/orders").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/store/orders/excel/cancel/waiting").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/store/orders/invoice/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/store/orders/excel/cancel/request").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/store/excel/inquiry").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/store/excel/review").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/users/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/users/{userNo}/content/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/users/{userNo}/order/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/goods/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/admin/partners/search/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/orders/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/orders/search/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/operation/boards/storeInquiry/**/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/orders/cancel/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/statistics/service/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/statistics/orders/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/statistics/glovvers/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/statistics/contents/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/statistics/stores/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/statistics/goods/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/statistics/keywords/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/coupons/system/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/calculates/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/store/calculate/excel").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/admin/calculates/orders/excel/{calculateNo}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/glovv/store/calculate/orders/excel/{calculateNo}").permitAll()

                // signUp store partner manager
                .antMatchers(HttpMethod.POST, "/api/partners").permitAll()
                .antMatchers(HttpMethod.POST, "/api/partners/on").permitAll()
                .antMatchers(HttpMethod.GET, "/api/partners/exist").permitAll()
                .antMatchers(HttpMethod.GET, "/api/partners/exist/store").permitAll()
                .antMatchers(HttpMethod.GET, "/api/partners/manager/{managerNo}").permitAll()

                // for manager
                .antMatchers(HttpMethod.POST, "/api/partners/managers/find/**").permitAll()

                // ### private ##############################################################################
                .antMatchers("/api/partners/managers/change/password").access("hasRole('ROLE_MANAGER')")
                .anyRequest().authenticated()
                .and()
                .cors().and()

                // filter
                .addFilterBefore(AuthenticationFilter(bestFinAccountService), UsernamePasswordAuthenticationFilter::class.java)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(bestFinAccessDeniedHandler)

    }

    // ignore check swagger resource
    override fun configure(web: WebSecurity) {
        web
                .ignoring().antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger/**")
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedOrigin("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}