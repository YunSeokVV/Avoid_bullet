# <p align="center">총알 피하기 </p>

##### 

<p align="center">전투기를 직접 조정하여 사방에서 날아오는 총알을 피하는 게임입니다.</p>



개발 인원 : 1명 <br>
개발 기간 : 1.5개월<br>



**목차**

1. 앱의기능
2. 사용한 기술들
3. 시연영상 링크



#  1. 앱의 기능




![](https://private-user-images.githubusercontent.com/43668299/337187203-fc5de11a-612e-4e3f-8af2-30e108e9777f.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTc4MzUyOTUsIm5iZiI6MTcxNzgzNDk5NSwicGF0aCI6Ii80MzY2ODI5OS8zMzcxODcyMDMtZmM1ZGUxMWEtNjEyZS00ZTNmLThhZjItMzBlMTA4ZTk3NzdmLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MDglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjA4VDA4MjMxNVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTQzOWUxZTI5MDA3MDhiYTk1MWZjZGE0YWI1MzM5NjQ2NDAyZWQ1NTJiNzQ3ZTkyZTE1ZmFhYTJkNzM0ODRjNTMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.NNSZSmvkQ7pAJ9pTe0cSaeo-Mxp0GV0EjGDhLEgsTYU)



![](https://private-user-images.githubusercontent.com/43668299/337187178-957c3e7a-7e51-4411-847f-000f714d8349.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTc4MzUyOTUsIm5iZiI6MTcxNzgzNDk5NSwicGF0aCI6Ii80MzY2ODI5OS8zMzcxODcxNzgtOTU3YzNlN2EtN2U1MS00NDExLTg0N2YtMDAwZjcxNGQ4MzQ5LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MDglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjA4VDA4MjMxNVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWQxMTkyYmQ0NWFlNTY0NzM5OGVmMzczOGFiYmMzNzlmM2YxZTYxZTBlZmYzZWE2OTdjNTJmZjU0ZmZhYmMzNzQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.rqYjuaxW5J7vAdw2gDda9Y7uPGgBP858N5uEzCn-LSs)

![](https://private-user-images.githubusercontent.com/43668299/337187213-4c6a5b3b-dd35-4bce-ae86-182629bbe68b.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTc4MzUyOTUsIm5iZiI6MTcxNzgzNDk5NSwicGF0aCI6Ii80MzY2ODI5OS8zMzcxODcyMTMtNGM2YTViM2ItZGQzNS00YmNlLWFlODYtMTgyNjI5YmJlNjhiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA2MDglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNjA4VDA4MjMxNVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWEwZGVjZDc0NDg5YTU2MDdjZTU4Y2ZjODg5YTVmZTFiNDAzYTc1YzQ3ZjFkMzRjN2IyNDgxZjUxYzcxYjJmYjAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.d_yNlgrggbib6FaXlIiqTL82U4Rz63nqKkHyI3MQbL8)

# 기능 상세설명

**1.회원가입/로그인**

 회원가입과 로그인이 가능합니다.

**2.메인화면**

 게임의 다양한 메뉴들을 선택할 수 있는 화면입니다.

**3.게임실행**

 사용자가 전투기를 조종해서 사방에서 날아오는 총알을 피합니다. 

 1초에 점수가1씩 올라갑니다.

**4.내 프로필 사진 설정**

 자신의 프로필 사진을 설정할 수 있습니다.

**5.게임오버**

 총알에 피격되면 게임오버됩니다. 자신의 최종점수를 확인하고 이를 공유할 수 있습니다.

**6.비행기 선택하기**

 원하는 비행기를 바꿔가며 선택할 수 있습니다.

**7.점수보기**

 게임을 플레이한 사용자들의 점수를 확인할 수 있습니다.

**8.개발자에게 메일 보내기**

 개발자에게 이메일을 보낼 수 있습니다.



# 2. 사용한 기술 & 라이브러리

- Language : Java

- SharedPreference

- NaverLogin

  


# 3. 시연영상 링크

https://www.youtube.com/watch?v=Ipn8Xl4PB3E&feature=youtu.be
