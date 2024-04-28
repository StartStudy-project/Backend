<div align="center">

###  Start Study
개발자들을 위한 온라인 스터디 플랫폼

</div>

## 프로젝트 소개

온라인 스터디 플랫폼으로 스터디 그룹을 쉽게 생성하고 참여할 수 있도록 하며, 사용자가 스터디를 개최하고 참여함으로써 지속적인 학습과 커뮤니티 형성을 지원하다.
개발자들에게는 스터디는 지식공유와 함께 성장의 핵심 요소 중 하나여서 많은 개발자들은 스터디를 이용한다. 이 플랫폼을 통해 프로젝트를 통해 인원을 모으거나 cs공부, 코딩테스트 공부 등 지속적인 성장을 할 수 있도록 지원한다.


## 프로젝트 핵심 기능

- 코딩테스트와 프로젝트, cs, 기타 카테고리를 나눔으로써 사용자가 필요한 스터디를 찾기 쉽도록 구성.
- 관심 게시글을 등록하면 마이페이지에 저장.

## 서비스 화면
|           메인화면                   |                        회원가입                    |     
| :------------------------------------------: | :------------------------------------------------: |
| <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/a295d05c-ab3e-4761-8311-d91f01ad6917"/> | <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/378f02a9-3614-435f-a33e-b8b3971862af"/> | 

|           비회원                   |                        상세페이지[관리자]               | 
| :------------------------------------------: | :------------------------------------------------: |
| <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/fd45bba1-964d-4393-b86b-6978dea5de2d"/> | <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/691cd102-caee-4813-ac92-fea20673d652"/> | 

|           글 상세(자신 게시글)                   |                        글상세(타인 게시글)   |     
| :------------------------------------------: | :------------------------------------------------: |
| <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/87dfe5a5-8355-4202-bd70-bd4bf4597a58"/> | <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/d8eacf3e-39e8-4e6e-a0bf-c1f99fa32a81"/> | 

|         글쓰기[모든 사용자]                   |                        마이페이지[모든 사용자]   |     
| :------------------------------------------: | :------------------------------------------------: |
| <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/3fbdc79f-8c60-49cc-b4fd-0ee5c19f3161"/> | <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/f4f264eb-37c9-4e55-b7a1-7a445d2a1f2a"/> | 


|         회원정보[관리자]                   |                        전체글[관리자]   |     
| :------------------------------------------: | :------------------------------------------------: |
| <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/9e2ae7b6-39b4-40a0-be28-3254825aa56d"/> | <img width="100%" src="https://github.com/Study-Blog-Project/Backend/assets/70208747/17d1e869-d8cb-4181-bff3-cb22efbd9241"/> | 

## 설계 및 협업 
### [Notion](https://rounded-raclette-de8.notion.site/6b818717ac554fef86a01748349fac7c?pvs=74)
  -  [figma](https://www.figma.com/file/PjRozZCGgmik4tW8T0ksiE/%EC%8A%A4%ED%84%B0%EB%94%94-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?type=design&node-id=0-1&mode=design&t=OR6coK5AHWePgQ1n-0)
  -  [기술스택](https://rounded-raclette-de8.notion.site/c7fd7fbbdeb2412597c0f3a1e8533e0f?pvs=74)
  -  [API 정의](https://rounded-raclette-de8.notion.site/API-4c057239d78b48b78665749aca53fa3e?pvs=4)


<details><summary style="color:skyblue"> Swagger </summary>

![image](https://github.com/Study-Blog-Project/Backend/assets/70208747/d6c86860-a500-440d-a99c-9d9f6333c2ab)
![image](https://github.com/Study-Blog-Project/Backend/assets/70208747/50a220b4-7eab-4cda-82be-b96dae4f6cce)


</details>


## 기술 스택

### BackEnd
![image](https://github.com/Study-Blog-Project/Backend/assets/70208747/68a1c916-288d-4191-bdca-37ae241b5cc5)


### Infra
![image](https://github.com/Study-Blog-Project/Backend/assets/70208747/076b0e32-07d0-4db2-b3c7-bfb7721be8b5)

## 프로젝트 아키텍처

### 백엔드
![image](https://github.com/Study-Blog-Project/Backend/assets/70208747/30704870-fa9a-44fa-99c8-98a63818c760)


### CI/CD
![image](https://github.com/Study-Blog-Project/Backend/assets/70208747/339ad38b-8d54-4dcd-960c-e542c751169b)

## ERD설계
![image](https://github.com/Study-Blog-Project/Backend/assets/70208747/92d9da1b-9163-4eab-a6d5-12fd09a5bacf)

## 주요 기능

<table align="center"><!-- 팀원 표 -->
  <tr>
   <th>
    공통
   </th>
   <th>
    사용자
   </th>
   <th >
    관리자
   </th>
   </tr>
  <tr>
   <td align="left" width="350px" class="공통">
    - 회원가입, 로그인
    <br/>
    - 메인페이지 및 글 상세 페이지 조회
         <br/>
    - 글 상세 페이지 조회
   </td>
   <td align="left" width="350px" class="사용자 및 관리자">
    - 사용자 마이페이지
    <br/>
    - 글쓰기 등록 및 수정 및 삭제
    <br/>
    - 댓글쓰기
   </td>
   <td align="left" width="350px" class="관리자">
    - 글 상세페이지 삭제
    <br/>
    - 전체글 페이지 
    <br/>
    - 회원 정보 페이지 
   </td>
  </tr>
</table>

## Member

|           Backend                    |                        Frontend                    |
| :------------------------------------------: | :------------------------------------------------: |
|  [전유진](https://github.com/jacomyou1026)  |  [원종대](https://github.com/blkaka66)  |  
