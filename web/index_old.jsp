<%@ page import="com.yahoo.data.Place" %>
<%@ page import="com.yahoo.data.Image" %>
<%@ page import="com.yahoo.Twitter" %>
<%@ page import="com.yahoo.Tumbr" %>
<%
	Place[] places = Twitter.getAvailablePlaces();

	String oweidTxt = request.getParameter("place");

	String[] trends = new String[0];

	int oweid = -1;
	if (oweidTxt != null) {
		oweid = Integer.parseInt(oweidTxt);
		trends = Twitter.getTrends(oweid);
	}
%>
<html>
<head>
<title>Current Trends</title>
</head>
<body>
<form method="Post">
<select name="place">
<%
	for (Place place : places) {
%>
  <option value="<%=place.getWoeid()%>" <%=(place.getWoeid() == oweid ? "selected":"")%>><%=place.getName()%></option>
<%
	
	}
%>
</select>
<input type="submit" value="Submit" />
</form>

<!-- Display Area -->
<br />
<%
	for (String trend : trends) {
		trend = trend.replaceAll("#", "");
		out.println("<b>" + trend + ":</b><br />");

		Image[] images = Tumbr.getImages(trend);
		for (Image image : images) {
			out.println("<img src='" + image.getUrl() + "' width='" + image.getWidth() + "' height='" + image.getHeight() + "' /><br/>");
		}
		out.println("<br />");
	}
%>

</body>
</html>