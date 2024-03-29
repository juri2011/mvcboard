# mvcboard
JSP로 사진 파일을 업로드 할 수 있는 mvc2 형식의 게시판 프로젝트 예제입니다. (성낙현의 jsp 자바 웹 프로그래밍 서적 참고)

## :computer: 개발 환경
* `JAVA11`
* `Eclipse`
* `oracle`
* `JSP`

## :memo: 요구사항
### 게시판 CRUD 작업
|Controller(클래스)|설명|SQL
|---|---|---|
|WriteController|서버에 파일 업로드, DB에 INSERT|`INSERT`
|ListController|페이지 처리, 검색 기능, 게시물 목록 가져오기|`SELECT`
|EditController|서버에 파일 업로드, 해당 데이터를 게시글 번호로 찾아와서 DB에 UPDATE|`UPDATE`
|ViewController|게시글 하나를 조회하고 업로드된 사진도 불러옴|`SELECT`
|PassController|수정/삭제시 사용자로부터 비밀번호를 입력받음|
|DownloadController|사용자가 사진 파일을 다운로드 받을 수 있게 함|

### 그 외 클래스
|클래스|설명|
|---|---|
|BoardPage|페이지 리스트를 처리하고 화면 하단에 페이지네이션을 표시하기 위한 클래스
|FileUtil|파일 업로드, 다운로드, 이름 수정 등의 기능을 제공하는 클래스
|JSFunction|유효성 검사 실패 혹은 예외가 발생했을 때 java에서 js의 alert를 사용할 수 있도록 작성된 클래스

## :open_file_folder: 구조
### 클래스
#### controller
* DownloadController
* EditController
* ListController
* PassController
* ViewController
* WriteController
#### domain
* MVCBoardDTO
#### mapper
* MVCBoardDAO
### views
* Default.jsp
* Edit.jsp
* List.jsp
* Pass.jsp
* View.jsp
* Write.jsp
## :wrench: 개선사항

## :bulb: 알게 된 점
* Web.xml에 페이지네이션과 파일 업로드 처리를 위해 필요한 기호상수를 등록할 수 있었다.
* multipart와 Part에 대해서 더 자세히 공부할 수 있었다.
