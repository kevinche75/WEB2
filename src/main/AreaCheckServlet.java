package main;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AreaCheckServlet extends HttpServlet {

    private ServletContext context;
    private ArrayList<Point> list;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean flagX = false;
        boolean flagY = false;
        boolean flagR = false;

        ArrayList<Double> arrayY = new ArrayList<>();
        double X = 0;
        double R = 0;
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        if(list==null){
            list=new ArrayList<Point>();
            context.setAttribute("list",list);
        }

        try{
            out.println("<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<title>Response</title>" +
                    "<meta charset='utf-8'>" +
                    "<link rel='stylesheet' type='text/css' href='table.css'>" +
                    "</head>" +
                    "<body>" +
                    "<div class = 'messages'>");

            //Валидация X

            try{
                X = Double.parseDouble(request.getParameter("x").replace(',','.').trim());
                if(X>5 || X<-5)
                    out.println("X is out of range<br>");
                else flagX = true;
            } catch (NumberFormatException e){
                out.println("X must be a number<br>");
            }

            //Валидация Y

            for(int i = -3; i<7; i++){
                if(request.getParameter("chb"+i)!=null){
                    try{
                        Double Y = new Double(request.getParameter("chb"+i).replace(',','.').trim());
                        if(Y > 5 || Y <-5){
                            out.println("Y is out of range<br>");
                        }
                        arrayY.add(Y);
                        flagY = true;
                    } catch (NumberFormatException e){
                        out.println("Y must be a number<br>");
                    }
                }
            }

            //Валидация R

            try{
                R = Double.parseDouble(request.getParameter("rb").replace(',','.').trim());
                if(R > 3 || R<-3){
                    out.println("R is out of range<br>");
                }
                flagR = true;
            } catch (NumberFormatException e){
                out.println("R must be a number<br>");
            }

            out.println("<br></div>");

            if(flagX&&flagY&&flagR){
                for(int i = 0; i < arrayY.size(); i++){
                    list.add(new Point(X, arrayY.get(i), R));
                }
                out.println("<p id = 'number'>" + arrayY.size() + "</p>");
            } else {
                out.println("<p id = 'number'>" + 0 + "</p>");
            }

            out.println("<table  align='center'>" +
                    "<tr id = 'header'>" +
                    "<th><h5>Coordinate X</h5></th>" +
                    "<th><h5>Coordinate Y</h5></th>" +
                    "<th><h5>Value R</h5></th>" +
                    "<th><h5>Probitie?</h5></th>" +
                    "</tr>");

            for(int i = list.size()-1; i > -1; i--){
                out.println("<tr>" +
                        "<td>" + list.get(i).getX() + "</td>" +
                        "<td>" + list.get(i).getY() + "</td>" +
                        "<td>" + list.get(i).getR() + "</td>" +
                        "<td>" + list.get(i).isInArea() + "</td>" +
                        "</tr>");
            }

            out.println("</table>");

        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/controller");
    }

    public class Point{

        private double x;
        private double y;
        private double r;
        private boolean isInArea;

        public Point(double x, double y, double r){
            this.x = x;
            this.y = y;
            this.r = r;
            setInArea();
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getR() {
            return r;
        }

        public boolean isInArea() {
            return isInArea;
        }

        private void setInArea() {
            if(x < 0 && y>0){
                isInArea = false;
                return;
            }
            if(x>=0 && y>=0){
                isInArea = x<=r/2 && y<=r;
                return;
            }
            if(x>0 && y<=0){
                isInArea = Math.pow(x, 2) + Math.pow(y,2) <= Math.pow(r/2, 2);
                return;
            }
            if(x<=0 && y<=0){
                isInArea = y >= -x-r;
                return;
            }
            isInArea = false;
        }
    }
}
