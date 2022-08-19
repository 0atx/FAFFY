

# 🌊 FAFFY - 패션 컨설팅 플랫폼
![타이틀이미지](./docs/READMEIMG/banner_1.png)

## 👉 [FAFFY Link Here!](https://i7a802.p.ssafy.io)

</br>

## 📹 시연 및 소개 영상 보기 : [UCC 링크](https://youtu.be/qcSYZu7wvzU)

</br>

## 📆 프로젝트 진행 기간
 - 2022.07.11(월) ~ 2022.08.19(금)  
 - SSAFY 7기 2학기 공통프로젝트

---

## 📖 FAFFY - 계획 배경
- **여러분의 옷, 혹시 옷장에서 잠들어 있지 않나요?**
- '다시입다' 캠페인의 20년 설문조사에 따르면 설문자의 50% 정도가 약 30%의 옷을 입지 않는다고 합니다. 이러한 현상의 이유로 Fast Fashion이 원인으로 꼽히고 있습니다. 'Fast Fashion'이란 세계적으로 대량 생산, 판매하는 패션업에서 최신 유행하는 옷들을 저렴한 가격과 빠른 상품회전으로 판매하는 것을 뜻합니다. 하지만 이는 의류를 다시 입지 않게 되는 현상을 야기해, 결국 의류를 버리게 되어 심각한 환경 문제를 발생시킵니다.
- 이 문제를 타파하기 위해 지속 가능한 패션을 위한 "FAFFY"를 생각해냈습니다. "옷장에 있는 옷들을 그냥 버리기 보단 새로운 코디를 통해 다시 입어볼순 없을까"라는 취지에서 생각해내게 되었습니다!

</br>

## 🔎 FAFFY - 개요
- ### **Find All Fashions For You 당신을 위한 모든 패션을 찾아드립니다!**  
  - FAFFY는 <span style="color:skyblue">F</span>ind <span style="color:skyblue">A</span>ll <span style="color:skyblue">F</span>ashions <span style="color:skyblue">F</span>or <span style="color:skyblue">Y</span>ou의 약자입니다.  
  FAFFY는 **패션 / 뷰티**에 능력 있는 사람들과, 이를 배우고 싶어하거나 관심있는 사람들을 위한 웹 화상 커뮤니티 플랫폼입니다.

</br>

---

## 주요 기능


- ### 컨설팅
    - WebRTC를 통해 언제 어디서든 웹캠만 있다면 패션 컨설팅이 가능합니다.
    - 패션 관련 인플루언서의 조언과 추천을 받아 새로운 코디를 입어볼수 있습니다.
    - 굳이 집안에 있는 옷으로 코디 추천이 아니더라도 체형별 옷입는 팁 등 조언 또한 받을 수 있습니다.
    <br/>
- ### 방구석 패션쇼
    - 컨설팅이 아니더라도 나의 패션을 사진보다 훨씬 역동적으로 보여줄 수 있습니다.
    - 나의 옷장에 있는 옷들을 꺼내 안입는 옷에 대한 의견 또한 들을 수 있습니다.
    - 요즘 유행하는 옷들에 대해 자유롭게 얘기를 나눠보세요!
    <br/>
- ### 커뮤니티
    - 시간이 없어 컨설팅이나 방구석 패션쇼에 참여를 못하신다면,
      게시글을 올려 얘기를 나눌 수 있습니다.
    - 다양한 주제로 패션관련 애기를 나누어도 좋고, 모르는 패션에 대한 질문 또한 가능하여
      소통의 장을 열 수 있습니다.
    <br/>
- ### 팔로우
    - 취향이 같거나 관심있는 유저를 팔로우해서 이후에도 계속해서 소식을 받을 수 있습니다.
    - 팔로우 맺은 친구들의 게시물이나 방송을 언제나 확인할 수 있습니다.
</br>

---

## 🛠️ 주요 기술

**Backend - Spring**
- IntelliJ IDE 2022.1.3****
- JDK 11.0.15.1
- Springboot 2.7.1
- Spring Data JPA
- Spring Security
- Spring Web
- Redis 3.0.504
- Swagger 2.0.0
- MySQL 8.0.29
- Hibernate 5.6.9 Final
- Swagger 2.9.2

**Frontend**
- Visual Studio Code IDE
- Node.Js 16.16.0 LTS
- Vue 2.7.7
- Vuetify 2.6.7
- Vuex 3.6.2
- OpenVidu 2.22.0

**CI/CD**
- AWS EC2 Ubuntu 20.04 LTS
- NGINX 1.18.0
- SSL 

## 🗂️ 시스템 아키텍처
![서비스_아키텍처]()

## ✔ 프로젝트 파일 구조
---
### Back
```
faffy-backend
  ├── config
  │   └── auth
  ├── controller
  ├── dto
  │   └── simple
  ├── exception
  ├── jpa
  │   ├── entity
  │   ├── repository
  │   └── type
  ├── mapping
  └── service
```
### Front
```
faffy-front
  ├── node_modules
  ├── public
  └── src
      ├── api
      ├── assets
      ├── components
      │   ├── auth
      │   ├── board
      │   ├── common
      │   ├── onair
      │   ├── search
      │   ├── user
      │   └── video
      ├── configs
      ├── plugins
      ├── router
      ├── store
      │   └── modules
      ├── utils
      └── views
```


## ✔ 협업 툴
---
- Git
- Notion
- JIRA
- MatterMost
- Webex

## ✔ 협업 환경
---
- Gitlab
  - 코드의 버전을 관리
  - 이슈 발행, 해결을 위한 토론
  - MR시, 팀원이 코드리뷰를 진행하고 피드백 게시
- JIRA
  - 매주 월요일 목표량을 설정하여 Sprint 진행
  - 업무의 할당량을 정하여 Story Point를 설정하고, In-Progress -> Done 순으로 작업  
- 회의
  - 매일 아침마다 Webex Scrum 진행, 전날 진도량과 당일 해야할 목표량 설정
  -
- Notion
  - 회의가 있을때마다 회의록 기록
  - 개발 중 참고할만 하거나, 도움이 될 문서를 해당 기술 분야에 공유
  - 컨벤션 정리
  - 스토리보드, 스퀀스다이어그램, 기능명세서 등 모두가 공유해야 하는 문서 관리


## ✔ 팀원 역할 분배
---
![역할 분배](./docs/READMEIMG/role.jpg)
- 팀장 : [이준성](https://github.com/js727r)
- 팀원 : [박윤하](https://github.com/0atx)
- 팀원 : [류경하](https://github.com/ryu4949)
- 팀원 : [김명석](https://github.com/audtjr9514)
- 팀원 : [김수만](https://github.com/SumanKim)
- 팀원 : [이용우](https://github.com/woojaang)

## ✔ 프로젝트 산출물

---
- [기능명세서](./docs/FunctionaSspecification/FunctionaSspecification.png)
- [와이어프레임](./docs/WIREFRAME/WIREFRAME.png)
- [아키텍처](./docs/)
- [컨벤션](./docs/)
- [API](./docs/)
- [ERD](./docs/ERD/ERD.jpg)
- [회의록](https://www.notion.so/5cab1f06588249b5b678d84724763349)
---

## ✔ 프로젝트 결과물
- [포팅메뉴얼](./exec/포팅메뉴얼_FAFFY.pdf)
- [중간발표자료](./ppt/PJT1중간발표/PJT1중간발표.pptx)
- [최종발표자료](./ppt/PJT1최종발표/PJT1최종발표.pptx)


