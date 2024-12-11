<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Added</title>
</head>
<body>
    <h1>Product Details</h1>
    <c:if test="${not empty addedProduct}">
        <h2>${addedProduct.productName}</h2>
        <img src="${addedProduct.imageUrl}" alt="${addedProduct.productName}" />
        <p>Original Price: ${addedProduct.originalPrice}</p>
        <p>Sale Price: ${addedProduct.salePrice}</p>
        <p>Number Left: ${addedProduct.numberLeft}</p>
        <p>Description: ${addedProduct.description}</p>
        <p>Brief Information: ${addedProduct.briefInformation}</p>
        <p>Categoryid: ${addedProduct.categoryId}</p>
        <p>stt ${addedProduct.status}</p>
        <p>img ${addedProduct.imageUrl}</p>
    </c:if>
    <c:if test="${not empty success}">
        <div>${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>
</body>
</html>
