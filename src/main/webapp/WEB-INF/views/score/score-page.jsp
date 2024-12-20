<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <!-- reset css -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.min.css">

  <!-- bootstrap css -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">


  <!-- bootstrap js -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>

  <!-- jquery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <style>
    label {
      display: block;
    }

    .score-list>li {
      margin-bottom: 10px;
    }

    .score-list>li:first-child {
      font-size: 1.2em;
      color: blue;
      font-weight: 700;
      border-bottom: 1px solid skyblue;
    }

    .del-btn {
      width: 10px;
      height: 10px;
      background: red;
      color: #fff;
      border-radius: 5px;
      margin-left: 5px;
      text-decoration: none;
      font-size: 0.7em;
      padding: 6px;
    }

    .del-btn:hover {
      background: orangered;
    }

    section.score {
      padding: 50px 50px 100px;
      font-size: 1.5em;
    }

    .list-header {
      display: flex;
      justify-content: space-between;

      width: 50%;
    }
    .list-header .sort-link-group {
      display: flex;

    }
    .list-header .sort-link-group div {
      margin-right: 20px;
    }

    .error {
      color: red;
    }
  </style>
</head>

<body>

<div class="wrap">

  <section class="score">
    <h1>${title} 애플리케이션</h1>

    <%--  JSP 주석
    <ul>
        <c:forEach var="f" items="${foods}">
            <li>${f}</li>
        </c:forEach>
    </ul>
    --%>

    <form id="score-form">
      <label>
        # 이름: <input type="text" name="name">
        <p class="error" id="studentName"></p>
      </label>
      <label>
        # 국어: <input type="text" name="kor">
        <p class="error" id="korean"></p>
      </label>
      <label>
        # 영어: <input type="text" name="eng">
        <p class="error" id="english"></p>
      </label>
      <label>
        # 수학: <input type="text" name="math">
        <p class="error" id="math"></p>
      </label>
      <label>
        <button id="createBtn" type="submit">확인</button>
        <button id="go-home" type="button">홈화면으로</button>
      </label>
    </form>

    <hr>

    <ul class="score-list">
      <li class="list-header">
        <div class="count">총 학생 수: <span id="count">0</span>명</div>
        <div class="sort-link-group">
          <div><a id="id" href="#">학번순</a></div>
          <div><a id="name" href="#">이름순</a></div>
          <div><a id="average" href="#">평균순</a></div>
        </div>
      </li>

      <!-- 학생 성적정보가 들어갈 부분 -->
      <li>
        <ul id="scores"></ul>
      </li>

    </ul>

  </section>
</div>


<script>
  const API_URL = '/api/v1/scores';

  const $scores = document.getElementById('scores');

  // 화면에 성적목록을 렌더링하는 함수
  function renderScoreList(data) {

    // 리셋
    $scores.innerHTML = '';

    // 총 학생 수 렌더링
    document.getElementById('count').textContent = data.length;


    data.forEach(({id, maskingName, sum, avg, rank}) => {
      $scores.innerHTML += `
                    <li data-score-id="\${id}">
                        # 이름: \${maskingName}, 총점: \${sum}점,
                        평균: \${avg}점, 석차: \${rank}
                        <a href='#' class='del-btn'>삭제</a>
                    </li>
                `;
    });
  }

  // 서버에서 성적 정보를 가져오는 요청 메서드
  async function fetchGetScores(sortType='id') {
    const res = await fetch(API_URL + `?sort=\${sortType}`);
    const data = await res.json();
    console.log(data);

    // 화면에 정보 렌더링
    renderScoreList(data);
  }

  // 서버로 성적 등록 POST요청을 전송하는 함수
  async function fetchPostScore({name, kor, eng, math}) {

    // 요청 시작시 에러메시지 리셋
    document.querySelectorAll('.error').forEach($errorParagraph => {
      $errorParagraph.textContent = '';
    });

    // POST요청은 단순히 요청만보내는게 아니라
    // 서버에 데이터를 제공해야함
    const res = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        studentName: name,
        korean: kor,
        english: eng,
        math: math
      })
    });
    if (res.status === 200) {
      // 등록된 내용을 렌더링
      fetchGetScores();
      document.getElementById('score-form').reset();
    } else if (res.status === 400) {
      // 서버의 에러 메시지 파싱
      const errorJson = await res.json();
      for (const property in errorJson) {
        document.getElementById(property).textContent = errorJson[property];
      }
    }
  }

  async function fetchDeleteScore(id) {
    const res = await fetch(`\${API_URL}/\${id}`, {
      method: 'DELETE'
    });
    if (res.status === 200) {
      fetchGetScores();
    } else {
      alert('삭제 실패!');
    }
  }

  //==== 이벤트 리스너 등록 ====//
  // 정렬처리 이벤트
  document.querySelector('.sort-link-group').addEventListener('click', e => {
    e.preventDefault();
    if (!e.target.matches('a')) return;
    const sortType = e.target.id;
    console.log('정렬기준: ', sortType);

    // 서버에 정렬기준을 가지고 목록 조회요청 전송
    fetchGetScores(sortType);

  });

  // 성적 정보 등록 이벤트
  document.getElementById('createBtn').addEventListener('click', e => {

    e.preventDefault(); // form의 submit시 발생하는 새로고침 방지

    const $form = document.getElementById('score-form');
    // formData객체 생성
    const formData = new FormData($form);
    const scoreObj = Object.fromEntries(formData.entries());
    console.log(scoreObj);

    // 서버로 POST요청 전송
    fetchPostScore(scoreObj);

  });

  // 삭제 요청 이벤트 등록
  $scores.addEventListener('click', e => {
    e.preventDefault();
    if (!e.target.matches('.del-btn')) return;

    // 서버에 삭제요청 전송
    // 클릭한 요소가 가진 서버 id를 읽어내야 함.
    const id = e.target.closest('li').dataset.scoreId;
    console.log('id: ', id);

    fetchDeleteScore(id);
  });

  //==== 실행 코드 ====//
  fetchGetScores();
</script>

</body>

</html>