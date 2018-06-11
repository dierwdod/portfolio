# Mobile Project

[SKWeather Project 설정]

- https://developers.skplanetx.com 에서 앱 등록 및 API KEY 발급
- com.project.skweather.utils 페키지의 WeatherValue 인터페이스에서 SKSKPlanet API KEY 입력


[LoginService Project 설정]

1. Google 로그인
   - Google Developer Console(https://console.developers.google.com)에서 프로젝트 생성, 앱 등록, OAurh 2.0 클라이언트 ID 생성
   - Firebase Console(https://console.firebase.google.com)에 Google Developer에서 생성한 프로젝트를 추가 
   - google-service.json 파일을 다운받아 프로젝트의 app 디렉토리 안에 저장

2. Facebook 로그인
   - Facebook Developer(https://developers.facebook.com/docs/facebook-login/android)에서 프로젝트 생성, 앱 등록 
   - res/strings.xml에 facebook_id와 fb_login_protocol_scheme의 값 추가
