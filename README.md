
# AutoDevLog-Server
- - -
## 기능소개
‘AutoDevLog’ 는 키워드를 기반으로 LLM(Large Language Model) 을 통해 블로그 게시글 작성 웹서비스 입니다. 다양한 블로그 게시글 중에서도 트러블 슈팅(Trouble Shooting) 게시글을 작성하는 가능을 제공합니다.

## 팀원 소개
| <div align="center">국혜경</div> | <div align="center">이승우</div> | <div align="center">이종인</div> | <div align="center">윤영민</div> |
| :--------------------------- | :-------------------------- | :-------------------------- | :-------------------------- |
| **담당기능**<br>1. Velog API 연동 및 배포<br>2. AutoDevLog 로그인 로직 구현 | **담당기능**<br>1. 워드 임베딩<br>2. 코사인 유사도 검사 | **담당기능**<br>1. OpenAI API 연동<br>2. 프롬프트 엔지니어링 | **담당기능**<br>1. 프론트 웹페이지 제작 및 배포<br>2. 프롬프트 엔지니어링 |
| [<img src="https://avatars.githubusercontent.com/u/84006880?v=4" width="200" height="200">](https://github.com/k0000k) | [<img src="https://avatars.githubusercontent.com/u/127829709?v=4" width="200" height="200">](https://github.com/ls-rain) | [<img src="https://avatars.githubusercontent.com/u/76873740?v=4" width="200" height="200">](https://github.com/bell-person-ii) | [<img src="https://avatars.githubusercontent.com/u/146945828?v=4" width="200" height="200">](https://github.com/DevDAN09) |
| <div align="center"><a href="https://github.com/k0000k">k0000k</a></div> | <div align="center"><a href="https://github.com/ls-rain">Is-rain</a></div> | <div align="center"><a href="https://github.com/bell-person-ii">bell-person-ii</a></div> | <div align="center"><a href="https://github.com/DevDAN09">DevDAN09</a></div> |

- - -
## 서비스 링크
[AutoDevLog](https://auto-dev-log-client-vercel.vercel.app/)

- - -
## 서비스 소개
### 1. 서비스 로직
1. 사용자로 부터 ‘issue’, ‘’inference’, ‘solution’ 에 해당하는 키워드를 입력 받으면, 키워드를 기반으로 LLM을 통해 게시글을 생성합니다. 
2. 생성된 게시글에 사용자가 제목을 붙이고, 업로드를 승인하면, 사용자의 velog 계정에 게시물이 업로드 됩니다.

<1. 키워드 입력>
![키워드 입력](https://github.com/AutoDevLog/AutoDevLog-Server/assets/76873740/67dc7ce3-bb13-40fc-8c6c-42bed7f88b0a)
<2. 키워드 기반 게시글 생성>
![키워드기반 게시글 생성](https://github.com/AutoDevLog/AutoDevLog-Server/assets/76873740/30d05e3d-47fc-4928-bb6f-b17904b3752e)
<3. 게시글 전송>
![게시글 업로드](https://github.com/AutoDevLog/AutoDevLog-Server/assets/76873740/058d2993-9b98-48f7-b10c-bf1c8c34622c)
<4. 벨로드 업로드>
![벨로그 업로드](https://github.com/AutoDevLog/AutoDevLog-Server/assets/76873740/07ae798c-a9a6-4cd1-80f4-657023f9942f)
[게시글 링크](https://velog.io/@tech-blog/SpringBoot-NoArgsConstructor-%EC%A0%91%EA%B7%BC-%EB%A0%88%EB%B2%A8)

- - -
## 기술 목록
- SpringBoot 3
- Spring Security 6
- Spring Webflux
- Redis
- OpenAI API
- React
- AWS
- Vercel
- - -
### 기대효과
- 핵심 키워드 만으로 게시글을 생성하여 기술블로그를 운영할 수 있다.
- 넷 상에 유의미한 양질의 블로그 글이 늘어난다.
- 글쓰기가 막연한 초보 블로거도 빠르게 글을 쓸수 있다.
- - -
### 향후 업데이트 기능
- 소셜 로그인 지원
- Velog 소셜 가입자 대상 서비스 제공
- 프롬프트 엔지니어링을 통한 게시글 생성시간 단축 및 게시글 완성도 제고
