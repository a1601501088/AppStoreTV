package com.basicframe.utils.filter;

import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.*;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

public class PatchcaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2681320318460605118L;
	private static ConfigurableCaptchaService cs = null;
	private static ColorFactory cf = null;
	private static RandomWordFactory wf = null;
	private static RandomFontFactory rff = null;
	//private static Random r = new Random();
	private static CurvesRippleFilterFactory crff = null;
	private static MarbleRippleFilterFactory mrff = null;
	private static DoubleRippleFilterFactory drff = null;
	private static WobbleRippleFilterFactory wrff = null;
	private static DiffuseRippleFilterFactory dirff = null;


	/**
	 * Constructor of the object.
	 */
	public PatchcaServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		wf = null;
		cf = null;
		cs = null;
		rff = null;
		super.destroy();
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		response.setContentType("image/png");
		response.setHeader("cache", "no-cache");
		HttpSession session = request.getSession(true);
		OutputStream os = response.getOutputStream();
		switch (3) {
		case 0:
			cs.setFilterFactory(crff);
			break;
		case 1:
			cs.setFilterFactory(mrff);
			break;
		case 2:
			cs.setFilterFactory(drff);
			break;
		case 3:
			cs.setFilterFactory(wrff);
			break;
		case 4:
			cs.setFilterFactory(dirff);
			break;
		}
		String seccode = EncoderHelper.getChallangeAndWriteImage(cs, "png", os);
		session.setAttribute("seccode", seccode);
		os.flush();
		os.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		super.init();
		cs = new ConfigurableCaptchaService();
		cf = new SingleColorFactory(new Color(25, 60, 170));
		wf = new RandomWordFactory();
		rff = new RandomFontFactory();
		crff = new CurvesRippleFilterFactory(cs.getColorFactory());
		drff = new DoubleRippleFilterFactory();
		wrff = new WobbleRippleFilterFactory();
		dirff = new DiffuseRippleFilterFactory();
		mrff = new MarbleRippleFilterFactory();
		rff.setMaxSize(20);
		rff.setMinSize(20);
		cs.setFontFactory(rff);
		cs.setWordFactory(wf);
		cs.setColorFactory(cf);
		cs.setWidth(70);
		cs.setHeight(32);
		wf.setMaxLength(4);
		wf.setMinLength(4);
	}

}
