<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  导航栏部分
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--导航条部分-->
<div id="navbar_part">
    <div class="masking"></div>
    <nav class="navbar navbar-pills navbar-fixed-top" id="side_bar" role="navigation">
        <ul class="nav sidebar-nav">
            <li class="sidebar-brand"><span class="glyphicon glyphicon-home"></span>&nbsp;优科优社</li>
            <li id="lab_sort_list_trigger"><a>科联&nbsp;<span class="glyphicon glyphicon-chevron-down"></span></a></li>
            <div id="lab_sort_list" style="display: none">
                <c:forEach items="${domainLabMap}" var="currEntry">
                    <p id="${currEntry.key.value0}" class="dropdown-menu-head">${currEntry.key.value1}</p>
                    <div style="display: none" class="dropdown-menu-list">
                        <c:forEach items="${currEntry.value}" var="currLab">
                            <a id="${currLab.value0}" class="labItem" href="#selected">${currLab.value1}</a>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
            <li id="club_sort_list_trigger"><a>社团&nbsp;<span class="glyphicon glyphicon-chevron-down"></span></a></li>
            <%--<div id="club_sort_list" style="display: none">--%>
                <%--<p class="dropdown-menu-head">活动</p>--%>
                <%--<div style="display: none" class="dropdown-menu-list">--%>
                    <%--<a id="" href="">篮球社啊</a>--%>
                    <%--<a id="" href="">外联社啊</a>--%>
                    <%--<a id="" href="">不知到社</a>--%>
                    <%--<a id="" href="">不知道的社</a>--%>
                    <%--<a id="" href="">舞蹈社有吧</a>--%>
                    <%--<a id="" href="">吃饭社肯定有啦</a>--%>
                <%--</div>--%>
                <%--<p class="dropdown-menu-head">语言</p>--%>
                <%--<div style="display: none" class="dropdown-menu-list">--%>
                    <%--<a id="" href="">鸟语社</a>--%>
                    <%--<a id="" href="">鸭语社</a>--%>
                    <%--<a id="" href="">法语把</a>--%>
                    <%--<a id="" href="">计算机</a>--%>
                    <%--<a id="" href="">推土机</a>--%>
                    <%--<a id="" href="">我再编一个社</a>--%>
                <%--</div>--%>
                <%--<p class="dropdown-menu-head">技术</p>--%>
                <%--<div style="display: none" class="dropdown-menu-list">--%>
                    <%--<a id="" href="">不知道啥社</a>--%>

                    <%--<a id="" href="">不知道啥社+2</a>--%>
                    <%--<a id="" href="">不知道啥社+3</a>--%>
                    <%--<a id="" href="">最后编一个社</a>--%>
                <%--</div>--%>
            <%--</div>--%>
            <li><a id="about_us" href="#">关于优米</a></li>
        </ul>
    </nav>
</div>
