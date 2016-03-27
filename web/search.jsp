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
    <form action="./search.jsp" method="GET" >
      <input name="q" type="text"<%if(searchParameter != null) {%> value="<%= escapeHtml4(searchParameter) %>"<%}%>/>
      <input type="submit" value="Find"/>
    </form>
    <br/>
    <%
      if (searchParameter != null) {
        SearchResults result = new StackExcangeSearch("stackoverflow").search(searchParameter);
        if (result != null && result.getItems().size() > 0) {
    %>
    <h2>Questions with "<%= escapeHtml4(searchParameter) %>" in title</h2>
    <table>
      <tr>
        <th>Title</th>
        <th>Who posted it</th>
        <th>And when</th>
      </tr>
      <%
          for (SearchItem item : result.getItems()) {
            if (item.isAnswered()) { %><tr class="answered"><% } else { %><tr><% }
      %>
        <td>
          <a href="<%= item.getLink() %>"><%= item.getTitle() %></a>
        </td>
        <td>
          <%= item.getOwner().getDisplayName() %>
        </td>
        <td>
          <%= escapeHtml4(item.getCreationDate().toString()) %>
        </td>
      </tr><%
          }
      %>
    </table><%
          if (result.isHasMore()) {
            %><p>There are more questions on the Stack Overflow.</p><%
          }
        } else {
          if (result.isHasError()) {
            %><p>An error had occurred while fetching results.</p><%
          } else {
            %><p>No questions found.</p><%
          }
        }
      }
    %>
  </body>
</html>
