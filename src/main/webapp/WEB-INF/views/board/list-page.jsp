<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>스프링 연습프로젝트 사이트</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Single+Day&display=swap" rel="stylesheet">

    <!-- reset -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.min.css">

    <!-- fontawesome css: https://fontawesome.com -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">

    <!-- https://linearicons.com/free#cdn -->
    <link rel="stylesheet" href="https://cdn.linearicons.com/free/1.0.0/icon-font.min.css">

    <!-- bootstrap css -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">


    <link rel="stylesheet" href="/assets/css/main.css">

    <!-- bootstrap js -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" defer></script>

    <!-- side menu event js -->
    <script src="/assets/js/side-menu.js" defer></script>

    <link rel="stylesheet" href="/assets/css/list.css">

    <style>
        .card-container .card .card-title-wrapper .time-view-wrapper>div.hit {
            background: yellow;
        }
    </style>

</head>

<body>

<!-- header -->
<header>
    <div class="inner-header">
        <h1 class="logo">
            <a href="/board/list">
                <img src="/assets/img/logo.png" alt="로고이미지">
            </a>
        </h1>

        <div class="profile-box">

        </div>

        <h2 class="intro-text">Welcome</h2>
        <a href="#" class="menu-open">
            <span class="menu-txt">MENU</span>
            <span class="lnr lnr-menu"></span>
        </a>
    </div>

    <nav class="gnb">
        <a href="#" class="close">
            <span class="lnr lnr-cross"></span>
        </a>
        <ul>
            <li><a href="/">Home</a></li>
            <li><a href="#">About</a></li>
            <li><a href="/board/list">Board</a></li>
            <li><a href="#">Contact</a></li>
            <li><a href="/members/sign-up">Sign Up</a></li>
            <li><a href="/members/sign-in">Sign In</a></li>
        </ul>
    </nav>

</header>

<div id="wrap">

    <div class="main-title-wrapper">
        <h1 class="main-title">꾸러기 게시판</h1>
        <button class="add-btn">새 글 쓰기</button>
    </div>


    <div class="top-section">
        <!-- 검색창 영역 -->
        <div class="search">
            <form>

                <select class="form-select" name="type" id="search-type">
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                    <option value="writer">작성자</option>
                    <option value="tc">제목+내용</option>
                </select>

                <input type="text" class="form-control" name="keyword">

                <button class="btn btn-primary" type="submit">
                    <i class="fas fa-search"></i>
                </button>

            </form>
        </div>

        <div class="amount">
            <div><a href="#">6</a></div>
            <div><a href="#">18</a></div>
            <div><a href="#">30</a></div>
        </div>

    </div>

    <div class="card-container">

        <!-- <div class="card-wrapper">
          <section class="card" data-bno="1">
            <div class="card-title-wrapper">
              <h2 class="card-title">하하호호</h2>
              <div class="time-view-wrapper">
                <div class="time">
                  <i class="far fa-clock"></i>
                  2024-12-18
                </div>
                <div class="view">
                  <i class="fas fa-eye"></i>
                  <span class="view-count">0</span>
                </div>
              </div>
            </div>
            <div class="card-content">
              가나다라마바사
            </div>
          </section>

          <div class="card-btn-group">
            <button class="del-btn">
              <i class="fas fa-times"></i>
            </button>
          </div>

        </div> -->
        <!-- end div.card-wrapper -->





    </div>
    <!-- end div.card-container -->

    <!-- 게시글 목록 하단 영역 -->
    <div class="bottom-section">

        <!-- 페이지 버튼 영역 -->
        <nav aria-label="Page navigation example">
            <ul class="pagination pagination-lg pagination-custom">

            </ul>
        </nav>

    </div>
    <!-- end div.bottom-section -->

</div>
<!-- end div.wrap -->


<!-- 모달 창 -->
<div class="modal" id="modal">
    <div class="modal-content">
        <p>정말로 삭제할까요?</p>
        <div class="modal-buttons">
            <button class="confirm" id="confirmDelete"><i class="fas fa-check"></i> 예</button>
            <button class="cancel" id="cancelDelete"><i class="fas fa-times"></i> 아니오</button>
        </div>
    </div>
</div>



<script>

    //====== 관련 전역변수 =====//
    const API_BASE_URL = '/api/v1/boards';

    const $cardContainer = document.querySelector('.card-container');

    //================= 삭제버튼 스크립트 =================//
    const modal = document.getElementById('modal'); // 모달창 얻기
    const confirmDelete = document.getElementById('confirmDelete'); // 모달 삭제 확인버튼
    const cancelDelete = document.getElementById('cancelDelete'); // 모달 삭제 취소 버튼

    $cardContainer.addEventListener('click', e => {
        // 삭제 버튼을 눌렀다면~
        if (e.target.matches('.card-btn-group *')) {
            console.log('삭제버튼 클릭');
            modal.style.display = 'flex'; // 모달 창 띄움

            // 여기서 클릭한 x버튼의 근처에 있는 card의 ID를 찾기
            const id = e.target
                .closest('.card-wrapper')
                .querySelector('.card')
                .dataset.bno;


            // 확인 버튼 이벤트
            confirmDelete.onclick = e => {
                // 삭제 처리 로직 - 서버에 DELETE요청
                const fetchDeleteBoard = async (id) => {
                    const res = await fetch(`\${API_BASE_URL}/\${id}`, {
                        method: 'DELETE'
                    });
                    if (res.status === 200) {
                        fetchGetBoardList();
                    } else {
                        alert('삭제 실패!');
                    }
                };


                fetchDeleteBoard(id);

                modal.style.display = 'none'; // 모달 창 닫기
            };


            // 취소 버튼 이벤트
            cancelDelete.onclick = e => {
                modal.style.display = 'none'; // 모달 창 닫기
            };
        }
    });

    // 전역 이벤트로 모달창 닫기
    window.addEventListener('click', e => {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    //========== 게시물 목록 스크립트 ============//

    function removeDown(e) {
        if (!e.target.matches('.card-container *')) return;
        const $targetCard = e.target.closest('.card-wrapper');
        $targetCard?.removeAttribute('id', 'card-down');
    }

    function removeHover(e) {
        if (!e.target.matches('.card-container *')) return;
        const $targetCard = e.target.closest('.card');
        $targetCard?.classList.remove('card-hover');

        const $delBtn = e.target.closest('.card-wrapper')?.querySelector('.del-btn');
        if ($delBtn) $delBtn.style.opacity = '0';
    }



    $cardContainer.onmouseover = e => {

        if (!e.target.matches('.card-container *')) return;

        const $targetCard = e.target.closest('.card');
        $targetCard?.classList.add('card-hover');

        const $delBtn = e.target.closest('.card-wrapper')?.querySelector('.del-btn');
        if ($delBtn) $delBtn.style.opacity = '1';
    }

    $cardContainer.onmousedown = e => {

        if (e.target.matches('.card-container .card-btn-group *')) return;

        const $targetCard = e.target.closest('.card-wrapper');
        $targetCard?.setAttribute('id', 'card-down');
    };

    $cardContainer.onmouseup = removeDown;

    $cardContainer.addEventListener('mouseout', removeDown);
    $cardContainer.addEventListener('mouseout', removeHover);

    // write button event
    document.querySelector('.add-btn').onclick = e => {
        window.location.href = '/board/write';
    };


    //====== 일반 함수 ======//
    // 화면에 게시물배열을 렌더링
    function renderBoardList(boardList) {
        $cardContainer.innerHTML = ''; // reset

        boardList.forEach(({id, title, content, regDateTime, viewCount}) => {
            $cardContainer.innerHTML += `
              <div class="card-wrapper">
                <section class="card" data-bno="\${id}">
                  <div class="card-title-wrapper">
                    <h2 class="card-title">\${title}</h2>
                    <div class="time-view-wrapper">
                      <div class="time">
                        <i class="far fa-clock"></i>
                        \${regDateTime}
                      </div>
                      <div class="view">
                        <i class="fas fa-eye"></i>
                        <span class="view-count">\${viewCount}</span>
                      </div>
                    </div>
                  </div>
                  <div class="card-content">
                    \${content}
                  </div>
                </section>

                <div class="card-btn-group">
                  <button class="del-btn">
                    <i class="fas fa-times"></i>
                  </button>
                </div>

              </div>
            `;
        });
    }

    //====== 서버 API통신 관련 함수 ======//
    async function fetchGetBoardList() {
        const res = await fetch(API_BASE_URL);
        const data = await res.json();
        console.log(data);

        // 렌더링
        renderBoardList(data);
    }


    // ====== 메인 실행 코드 ===== //
    // 서버에 목록 조회 API요청 보내기
    fetchGetBoardList();

</script>



</body>

</html>