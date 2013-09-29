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

<!DOCTYPE html>
<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		<script type="text/javascript" src="/js/masonry.pkgd.min.js"></script>
		<script type="text/javascript" src="/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(function(){
				var container = document.querySelector('.masonry');
				var msnry = new Masonry( container, {
			  		columnWidth: 100,
			  		itemSelector: '.box',
			  		gutter: 5
				});
				$(".page").hide();
				$(".page.active").show();
			

				$(".link").click(function(event){
					event.preventDefault();
					console.log(this.id);
					console.log("clicked");
					
					$("#titleTrend").html(this.id);
					
					$(".page").hide();
					$(".page#"+this.id).show();
				});
			});
			
			function doSubmitForm() {
			    document.placeForm.submit();
			}
			
		</script>
		<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
		<style type="text/css">
			#container {
			  background: #FFF;
			  padding: 5px;
			  margin-bottom: 0px;
			  border-radius: 5px;
			  clear: both;
			  margin: 0 auto;
  			  width: auto;
			}
			.centered { margin: 0 auto; }

			.box {
			  margin: opx;
			  padding: 5px;
			  background: #D8D5D2;
			  font-size: 11px;
			  line-height: 1.4em;
			  float: left;

			  box-shadow: 5px 5px 5px #888888;
			}

			.box h2 {
			  font-size: 14px;
			  font-weight: 200;
			}

			.box img {
			  display: block;
			  width: 100%;
			}

			.col1 { width: 80px; }
			.col2 { width: 180px; }
			.col3 { width: 280px; }
			.col4 { width: 380px; }
			.col5 { width: 480px; }

			.col1 img { max-width: 80px; }
			.col2 img { max-width: 180px; }
			.col3 img { max-width: 280px; }
			.col4 img { max-width: 380px; }
			.col5 img { max-width: 480px; }

			/**** Clearfix ****/
			.clearfix:before, .clearfix:after { content: ""; display: table; }
			.clearfix:after { clear: both; }
			.clearfix { zoom: 1; }

			body{
				width: 100%;
				height: 100%;
				background-image:url('/images/notebook.png');
			}
			
			#places{
				margin-top: 20px;
			}

		</style>
	</head>
	<body>
		<div class="navbar navbar-fixed-top">
	    	<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="#">&iquest; Watz Going On ?</a> &nbsp;
					<div id="places">
					<center>
					<form name="placeForm" method="Post">
						<select name="place">
						<%
							for (Place place : places) {
						%>
						  <option value="<%=place.getWoeid()%>" <%=(place.getWoeid() == oweid ? "selected":"")%>><%=place.getName()%></option>
						<%
							
							}
						%>
						</select>
						<input type="submit" class="btn" value="Submit" />
					</form>
					</center>
					</div>
				</div>
			</div>
		</div>
		
		<div class="container">
			<div class="row">
				<div class="span2">
					
					<ul class="nav nav-list">
						<li class="nav-header"><h4>Trendzing</h4></li>
						<%
							for (String trend : trends) {
								trend = trend.replaceAll("#", "");
						%>
							<li id="<%=trend%>" class="link active"><a href="#<%=trend%>"><%=trend%></a></li>
						<%
							}
						%>
					</ul>
				</div>	
	    		
	    		<div class="span10">
	    			<center><div id="titleTrend">All Trends</div></center>
	    		 
	    		<%
					for (String trend : trends) {
						trend = trend.replaceAll("#", "");
	    		%>
	    		  <div id="<%=trend%>" class="page active">
	    		  	<div class="masonry">
	    		 		<%
	    		 		Image[] images = Tumbr.getImages(trend);
	    		 		for (int i = 0; i < images.length; i++) {
	    		 			if (i % 2 == 0) {
	    		 		%>
	    		 			<div class="box col3"><img src="<%=images[i].getUrl()%>"></div>
	    		 		<%
	    		 			} else {
	    		 		%>
	    		 			<div class="box col1"><img src="<%=images[i].getUrl()%>"></div>
						<%
	    		 			}
	    		 		}
						%>

					
			  		</div>
	    		  </div>
	    		<%
	    			}
	    		%>
	    	</div>			
	  	</div>
	  </div>
		
	</body>
</html>