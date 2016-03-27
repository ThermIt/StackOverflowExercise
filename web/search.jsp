<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 27.03.16
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         import="stackExchange.SearchResults"
         import="stackExchange.SearchItem"
         import="stackExchange.StackExcangeSearch"
         import="static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4" %>
<html>
  <head>
    <title>Search Stack Overflow question with text in title</title>

    <link rel="stylesheet" href="./css/search.css" type="text/css" />
  </head>
  <body>
    <%
      String searchParameter = request.getParameter("q");
    %>
    <form action="/search.jsp" method="GET" >
      <input name="q" type="text"<%if(searchParameter != null) {%> value="<%= escapeHtml4(searchParameter) %>"<%}%>/>
      <input type="submit" value="Find"/>
    </form>
    <br/>
    <%
      if (searchParameter != null) {
    %>
    <h2>Questions containing "<%= escapeHtml4(searchParameter) %>"</h2>
    <table>
      <tr>
        <th>Title</th>
        <th>Who posted it</th>
        <th>And when</th>
      </tr>
      <%
        SearchResults result = new StackExcangeSearch("stackoverflow").search(searchParameter);

        for (SearchItem item : result.getItems()) { %>
      <% if (item.isAnswered()) { %><tr class="answered"><% } else { %><tr><% } %>
        <td>
          <a href="<%= item.getLink() %>"><%= escapeHtml4(item.getTitle()) %></a>
        </td>
        <td>
          <%= escapeHtml4(item.getOwner().getDisplayName()) %>
        </td>
        <td>
          <%= escapeHtml4(item.getCreationDate().toString()) %>
        </td>
      </tr><%
        }
      }
      %>
    </table>
  </body>
</html>
