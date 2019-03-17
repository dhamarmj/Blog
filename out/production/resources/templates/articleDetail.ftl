<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Blog</title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
</head>
<body>
<#if userSigned == false>
    <#include "firstNav.ftl">
<#else>
    <#include "navbar.ftl">
</#if>
<br>
<br>
<br>
<br>
<header class="masthead">
    <div class="overlay"></div>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <div class="post-heading">
                    <h1>${articulo.getTitulo()}</h1>
                    <br>
                    <blockquote class="blockquote">Posted by
                        ${articulo.getUsuarioautor().getNombre()}
                     on ${articulo.getFecha()?string.long}</blockquote>
                </div>
            </div>
        </div>
    </div>
</header>
<br>
<article>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
                <p>${articulo.getTexto()}</p>
                <blockquote class="blockquote">${articulo.getStringEtiqueta()}</blockquote>
            </div>
        </div>
    </div>
</article>
<br>
<br>
<hr><#include "footer.ftl">
<script src="/jquery/jquery.js"></script>
<script src="/js/bootstrap.bundle.js"></script>

</body>
</html>