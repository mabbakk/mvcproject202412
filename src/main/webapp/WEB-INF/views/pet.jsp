<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>

  <style>
    .title {
      background: aquamarine;
      color: cornflowerblue;
    }
  </style>
</head>
<body>
<h1 class="title">Pet.jsp파일입니다~~~</h1>
<h2>메롱메롱~~</h2>
<p id="content"></p>

<script>
  const $p = document.getElementById('content');
  fetch('/products')
          .then(res => res.json())
          .then(data => {
            console.log(data);
            data.forEach(product => {
              $p.innerHTML += `
                <div>
                    제품명: \${product.name},
                    가격: \${product.price}
                </div>
               `;
            });
          });
</script>
</body>
</html>