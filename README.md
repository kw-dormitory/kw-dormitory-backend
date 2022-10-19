# KW dormitory notification
### Perpose
> - 기숙사 공지 API 제작
> - 통금시간 알림 push 알림 기능 제공
> - 주기적인 모니터링을 통한 새로운 공지 소식 push 알림 제공
> - 익명 오픈카톡 파티 모집에 대한 API 제공


### Technical Stack
> > #### Back-End
> > - Spring MVC
> > - Spring Batch
> > - Spring Data JPA
> > - FCM(Firebase Cloud Messaging)
> > - Spring webflux (for using webClient)
> 
> > #### Database
> > - MySQL
> 
> > #### Test
> > - Junit5
> > - Mockito
> 
> > #### Function
> > - 기숙사 공지 조회 API(with pagination)
> > - 기숙사 공지 모니터링 및 알림 push 기능
> > - 익명 파티 생성, 조회
