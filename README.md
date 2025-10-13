 카카오 oauth 로그인 기능
----------------------------

   <img width="600" height="600" alt="Image" src="https://github.com/user-attachments/assets/b90e6ac4-e136-493e-9c0c-1b8fe5e055cb" />


   implementation 'org.springframework.boot:spring-boot-starter-oauth2-client' 라이브러리 사용.

   사용자가 /oauth2/authorization/kakao 로 로그인 시도
   
   -> OAuth2LoginAuthenticationFilter 가 요청을 가로채고, Authorization Code → AccessToken 교환

   -> securityConfig에서 설정한 CustomOAuth2UserService.loadUser() 실행

   -> 회원가입되어 있지 않은 경우 회원정보 DB에 저장

   -> loadUser()의 리턴값(OAuth2User)이 Authentication 객체의 Principal로 들어감

   -> 최종적으로 SecurityContext에 저장 → 로그인된 사용자 정보로 활용 가능
   
