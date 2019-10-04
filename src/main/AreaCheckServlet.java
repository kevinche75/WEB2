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

    @Override
    public void destroy() {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean flagX = false;
        boolean flagY = false;
        boolean flagR = false;

        ArrayList<Float> arrayY = new ArrayList<>();
        float X = 0;
        float R = 0;
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
                    "<title>Result</title>" +
                    "<meta charset='utf-8'>" +
                    "<link rel='stylesheet' type='text/css' href='table.css'>" +
                    "</head>" +
                    "<body>" +
                    "<div class = 'messages'>");

            //Валидация X

            try{
                X = Float.parseFloat(request.getParameter("x").replace(',','.').trim());
                if(X>5 || X<-5)
                    out.println("Введённое значение X вне диапозона<br>");
                else flagX = true;
            } catch (NumberFormatException e){
                out.println("Введённое значение X должно быть числом<br>");
            }

            //Валидация Y

            for(int i = -3; i<7; i++){
                if(request.getParameter("chb"+i)!=null){
                   try{
                       Float Y = new Float(request.getParameter("chb"+i).replace(',','.').trim());
                       if(Y > 5 || Y <-5){
                           out.println("Введённое значение Y вне диапозона<br>");
                       }
                        arrayY.add(Y);
                        flagY = true;
                   } catch (NumberFormatException e){
                       out.println("Введённое значение Y должно быть числом<br>");
                   }
                }
            }

            //Валидация R

            try{
                R = Float.parseFloat(request.getParameter("rb").replace(',','.').trim());
                if(R > 3 || R<-3){
                    out.println("Введённое значение R вне диапозона<br>");
                }
                flagR = true;
            } catch (NumberFormatException e){
                out.println("Введённое значение R должно быть числом<br>");
            }

            out.println("<br></div>");

            if(flagX&&flagY&&flagR){
                for(int i = 0; i > -1; i--){
                    list.add(new Point(X, arrayY.get(i), R));
                }
            }

        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/control");
    }

    public class Point{

        private float x;
        private float y;
        private float r;
        private boolean isInArea;

        public Point(float x, float y, float r){
            this.x = x;
            this.y = y;
            this.r = r;
            setInArea();
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getR() {
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
                isInArea = Math.pow(x, 2) + Math.pow(y,2) <= Math.pow(r, 2);
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
