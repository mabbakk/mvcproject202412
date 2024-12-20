<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">

<head>
                <%--head 분할 파일!!--%>
    <%@ include file="include/static-file.jsp"%>

    <!-- ck editor -->
    <link rel="stylesheet" href="https://cdn.ckeditor.com/ckeditor5/44.1.0/ckeditor5.css" />
    <script src="https://cdn.ckeditor.com/ckeditor5/44.1.0/ckeditor5.umd.js"></script>

    <style>
        .form-container {
            width: 500px;
            margin: auto;
            padding: 20px;
            background-image: linear-gradient(135deg, #a1c4fd, #fbc2eb);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 4px;
            font-size: 18px;
        }

        .form-container h1 {
            font-size: 40px;
            font-weight: 700;
            letter-spacing: 10px;
            text-align: center;
            margin-bottom: 20px;
            color: #ffffff;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-size: 20px;
        }

        input[type="text"],
        textarea {
            font-size: 18px;
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 2px solid #ffffff;
            border-radius: 8px;
            margin-bottom: 10px;
            background-color: rgba(255, 255, 255, 0.8);
        }

        textarea {
            resize: none;
            height: 200px;
        }

        .buttons {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
        }

        button {
            font-size: 20px;
            padding: 10px 20px;
            border: none;
            margin-right: 10px;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s;
        }

        button.list-btn {
            background: #e61e8c;
        }

        button:hover {
            background-color: #3d8b40;
        }

        button.list-btn:hover {
            background: #e61e8c93;
        }
        .ck-editor__editable {
            height: 300px;
        }
        .ck-editor__editable p {
            margin: 0;
        }
        .error {
            color: #f00;
            font-size: 0.9em;
            margin-left: 15px;
        }
        .label-container {
            display: flex;
            align-items: center;
        }
    </style>
</head>

<body>
        <%--header 분할 파일!!--%>
<%@ include file="include/header.jsp"%>


<div id="wrap" class="form-container">
    <h1>꾸러기 게시판 글쓰기</h1>
    <form id="board-form" novalidate>
        <label for="title">작성자</label>
        <input type="text" id="writer" name="writer" value="익명">

        <div class="label-container">
            <label for="title">제목</label> <span class="error" id="title"></span>
        </div>
        <input type="text" id="title" name="title" required>

        <div class="label-container">
            <label for="content">내용 </label>
        </div>
        <textarea id="content" name="content" maxlength="200" required></textarea>
        <div class="buttons">
            <button class="list-btn" type="button"
                    onclick="window.location.href='/board/list'">목록</button>
            <button type="submit">글쓰기</button>
        </div>
    </form>
</div>
<script>
    // CKEDITOR ...
    let editor;
    const {
        ClassicEditor,
        Essentials,
        Bold,
        Italic,
        Font,
        Paragraph
    } = CKEDITOR;

    ClassicEditor
        .create( document.querySelector( '#content' ), {
            licenseKey: 'eyJhbGciOiJFUzI1NiJ9.eyJleHAiOjE3NjYxMDIzOTksImp0aSI6IjEzODIxZWU2LTY0OWItNGE1OC04ZTA2LThlNzNhM2RlMTg4NiIsInVzYWdlRW5kcG9pbnQiOiJodHRwczovL3Byb3h5LWV2ZW50LmNrZWRpdG9yLmNvbSIsImRpc3RyaWJ1dGlvbkNoYW5uZWwiOlsiY2xvdWQiLCJkcnVwYWwiXSwiZmVhdHVyZXMiOlsiRFJVUCIsIkJPWCJdLCJ2YyI6IjM4MTM3YjhjIn0.Vy670BGfDF08y8-WPxehiMxEdzZwv99XOYbmPweVv1NOisMc-GE3PnTEY6pwz6pmeooWe5lArtch1r9iykDmfQ',
            plugins: [ Essentials, Bold, Italic, Font, Paragraph ],
            toolbar: [
                'undo', 'redo', '|', 'bold', 'italic', '|',
                'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor'
            ],
        } )
        .then(newEditor => {
            // editor = newEditor;
        })
        .catch(err => console.error(err));
</script>

<!-- custom script -->
<script>
    const API_BASE_URL = '/api/v1/boards';
    const $form = document.getElementById('board-form');

    // 에러 메시지 처리
    function createErrorMessage(errorObj) {
        // 기존 에러 메시지 정리
        const $errors = document.querySelectorAll('.error');
        $errors.forEach($err => $err.textContent = '');


        // 새 에러메시지 세팅
        for (const key in errorObj) {
            if (key === 'content') {
                const $errorSpan = document.createElement('span');
                $errorSpan.classList.add('error');
                $errorSpan.textContent = errorObj[key];
                document.querySelector('label[for=content]').after($errorSpan);
            }
            document.getElementById(key).textContent = errorObj[key];

        }
    }


    // POST 요청 서버로 보내기 함수
    async function fetchPost(payload) {
        const res = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        if (res.status === 200) {
            alert('게시물이 등록되었습니다.');
            // 목록으로 링크이동
            window.location.href='/board/list';
        } else if (res.status === 400) {
            const errorObj = await res.json();
            createErrorMessage(errorObj);
        }
    }

    $form.addEventListener('submit', e => {
        e.preventDefault(); // 새로고침 방지 (기본 동작 방지)

        // payload(서버로 보낼 데이터) 만들기
        // const payload = {
        //   title: document.getElementById('title').value,
        //   content: document.getElementById('content').value
        // };

        const formData = new FormData($form);
        // const payload = {
        //   title: formData.get('title'),
        //   content: formData.get('content'),
        // };

        const payload = Object.fromEntries(formData.entries());
        console.log('payload: ', payload);


        // 서버에 POST요청
        fetchPost(payload);

    });

</script>

</body>

</html>


</body>

</html>