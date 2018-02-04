package com.joinhawaii.util;

import java.text.*;
import java.util.*;
import java.io.*;
import java.util.regex.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class StringUtil {

	public static int LINE_PER_PAGE	= 15;

	public static int PAGE_MOVE = 10;

	public static String home = "";


	public static String nvl(Object str)
	{
		if ("____.__.__".equals(str)) {
			str = "";
		}

		if (str == null) {
			return "";
		}
		else if (str instanceof java.lang.Integer) {
			return str.toString();
		}
		else {
			return (String)str;
		}
	}

	public static String nvl(Object str, String replace)
	{
		if ("____.__.__".equals(str)) {
			str = "";
		}

		if (str == null || str.equals("")) {
			return replace;
		}
		else {
			return (String)str;
		}
	}

	public static boolean isAlphaNumber(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			char value = str.toUpperCase().charAt(i);

			if (value < 48 || value > 90 || ( value > 57 && value < 65))
			{
				return false;
			}
		}
		return true;
	}

	/**
	* 8859_1 ?óê?Ñú EUC-KR Î°? ?ù∏ÏΩîÎî© ?ïú?ã§.
	* @param str ?õêÎ≥? String
	* @return String Î≥??ôò?êú ?Ç¥?ö©
	*/
	public static String toKor(Object str){
		if (str == null)	return "";
		String rstr = "";
		try{
		  rstr=(str==null)?"":new String(((String)str).getBytes("8859_1"),"utf-8");
		} catch(java.io.UnsupportedEncodingException e) {
		}
		return rstr;
	}

	/**
	* 8859_1 ?óê?Ñú EUC-KR Î°? ?ù∏ÏΩîÎî© ?ïú?ã§.
	* @param str ?õêÎ≥? String
	* @return String Î≥??ôò?êú ?Ç¥?ö©
	*/
	public static String toEng(String str){
		if (str == null)	return null;
		String rstr=null;
		try{
		  rstr=(str==null)?"":new String(str.getBytes("euc-kr"),"8859_1");
		} catch(java.io.UnsupportedEncodingException e) {
		}
		return rstr;
	}

	/**
	* 8859_1 ?óê?Ñú EUC-KR Î°? ?ù∏ÏΩîÎî© ?ïú?ã§.
	* @param str ?õêÎ≥? String
	* @return String Î≥??ôò?êú ?Ç¥?ö©
	*/
	public static String decode(String str, String enc){
		if (str == null)	return "";
		String rstr = "";
		try{
			rstr = java.net.URLDecoder.decode(str, enc);
		} catch (java.io.UnsupportedEncodingException e) {
		}
		return rstr;
	}

	/**
	* Í∏àÏï°?óê Ï≤úÎã®?úÑ ÏΩ§ÎßàÎ•? Ï∞çÎäî?ã§.
	* @param	?õêÎ≥? ?à´?ûê
	* @return	ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
	*/
	public static String toPrice(long sumPrice)
	{
		DecimalFormat df = new DecimalFormat("###,###,###");

		return df.format(sumPrice);
	}


	/**
	* Í∏àÏï°?óê Ï≤úÎã®?úÑ ÏΩ§ÎßàÎ•? Ï∞çÎäî?ã§.
	* @param	?õêÎ≥? ?à´?ûê
	* @return	ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
	*/
	public static String toPrice(String sumPrice)
	{
		if (sumPrice == null || sumPrice.trim().equals("")) {
			return "";
		}

		DecimalFormat df = new DecimalFormat("###,###,###");

		return df.format(Long.parseLong(sumPrice));
	}

	/**
	* Í∏àÏï°?óê Ï≤úÎã®?úÑ ÏΩ§ÎßàÎ•? Ï∞çÎäî?ã§.
	* @param	?õêÎ≥? ?à´?ûê
	* @return	ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
	*/
	public static String toEngPrice(String sumPrice)
	{
		if (sumPrice == null || sumPrice.trim().equals("")) {
			return "";
		}

		DecimalFormat df = new DecimalFormat("###,###,###.##");

		return df.format(Double.parseDouble(sumPrice));
	}

	/**
	* ?Ç†?ûê?òï?ãù?ùÑ Î∞òÌôò
	* @param	?õêÎ≥? ?à´?ûê
	* @return	ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
	*/
	public static String toDate(String aDate, String delimeter)
	{
		if (aDate == null) {
			return "";
		} else if (aDate.length() == 8) {
			return aDate.substring(0,4) + delimeter + aDate.substring(4,6) + delimeter + aDate.substring(6);
		} else if (aDate.length() == 6) {
			return aDate.substring(0,4) + delimeter + aDate.substring(4);
		} else if (aDate.length() == 4) {
			return aDate.substring(0,2) + delimeter + aDate.substring(2);
		} else {
			return aDate;
		}
	}

	/**
	* ?Ç†?ûê?òï?ãù?ùÑ Î∞òÌôò
	* @param	?õêÎ≥? ?à´?ûê
	* @return	ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
	*/
	public static String toDate(String aDate)
	{
		return toDate(aDate, "-");
	}

	/**
        * ?Ç†?ûê?òï?ãù?ùÑ Î∞òÌôò
        * @param        ?õêÎ≥? ?à´?ûê
        * @return       ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
        */
	public static String toSimpleDate(String str, String filler)
	{
		if (str.length() == 8) {
			return str.substring(2,4) + filler + str.substring(4,6) + filler + str.substring(6);
		} else if (str.length() == 6) {
			return str.substring(2,4) + filler + str.substring(4);
		} else {
			return str;
		}
	}

	/**
        * ?Ç†?ûê?òï?ãù?ùÑ Î∞òÌôò
        * @param        ?õêÎ≥? ?à´?ûê
        * @return       ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
        */
	public static String toSDate(String str)
	{
		if (str.length() == 8) {
			return str.substring(4,6) + "/" + str.substring(6);
		} else {
			return str;
		}
	}


    /**
    * ?Ç†?ûê?òï?ãù?ùÑ Î∞òÌôò
    * @param        ?õêÎ≥? ?à´?ûê
    * @return       ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
    */
    public static String toEngDate(String str, String filler, boolean bool)
    {
		int pos = 0;

		if (bool) pos = 2;

        if (str.length() == 8) {
                return str.substring(4,6) + filler + str.substring(6) + filler + str.substring(pos,4);
        } else if (str.length() == 6) {
                return str.substring(4) + filler + str.substring(pos,4);
        } else {
                return str;
        }
    }

	/**
	* ?Ç†?ûê?òï?ãù?ùÑ Î∞òÌôò
	* @param	?õêÎ≥? ?à´?ûê
	* @return	ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
	*/
	public static String fromDate(String aDate)
	{
		if (aDate == null) {
			return "";
		} else {
			return aDate.replaceAll("-", "");
		}
	}

	/**
	* ?Ç†?ûê?òï?ãù?ùÑ Î∞òÌôò
	* @param	?õêÎ≥? ?à´?ûê
	* @return	ÏΩ§Îßà Ï∞çÏ?? ?à´?ûê.
	*/
	public static String toOX(String sYN)
	{
		if ("Y".equals(sYN)) {
			return "O";
		} else if ("N".equals(sYN)) {
			return "X";
		} else {
			return sYN;
		}
	}

	public static int parseInt(Object str)
	{
		if (str == null || str.equals("")) {
			return 0;
		} else {
			if (str instanceof String) {
				return Integer.parseInt((String)str);
			} else if (str instanceof Integer) {
				return ((Integer)str).intValue();
			} else {
				return 0;
			}
		}
	}



	public static String checked(Object str, Object str2)
	{
		if (str == null || str2 == null)	return "";

		if (str.equals(str2))				return "checked=\"checked\"";
		else								return "";
	}

	public static String selected(Object str, Object str2)
	{
		if (str == null || str2 == null)	return "";

		if (str.equals(str2))				return "selected=\"selected\"";
		else								return "";
	}

	public static String toHTML(String org)
	{
		return org.replaceAll("\n", "<br/>");
	}

	public static String toTag(String org)
	{
		org = org.replaceAll("'", "&apos;");

		return org;
	}

	public static String noBR(String org)
	{
//		return org.replaceAll("\n", "<BR>");

		if (org == null)	return org;

		StringBuffer temp = new StringBuffer();

		int pos = -1;
		for (int i = 0; i < org.length(); i++)
		{
			if (org.charAt(i) == 13)
			{
				temp.append(org.substring(pos+1, i));
				pos = i+1;
			}
		}

		temp.append(org.substring(pos+1));

		return temp.toString();
	}

	public static String toReply(String org)
	{
//		return org.replaceAll("\n", "<BR>");

		if (org == null)	return org;

		StringBuffer temp = new StringBuffer();

		int pos = -1;
		for (int i = 0; i < org.length(); i++)
		{
			if (org.charAt(i) == 13)
			{
				temp.append(">" + org.substring(pos+1, i));
				pos = i+1;
			}
		}

		temp.append(">" + org.substring(pos+1));

		return temp.toString();
	}


	public static String substring(String org, int lastIndex)
	{

		return substring(noHTML(org), 0, lastIndex, "..");
	}


	public static String substring(String org, int lastIndex, String tail)
	{
		return substring(org, 0, lastIndex, tail);
	}


	public static String substring(String org, int startIndex, int lastIndex)
	{
		return substring(org, startIndex, lastIndex, "");
	}

	public static String substring(String org, int startIndex, int lastIndex, String tail)
	{
		if (org == null || org.equals(""))	return "";

		if (org.length() <= lastIndex)
		{
			return org;
		}

		return org.substring(startIndex, lastIndex) + tail;
	}

	// fromIndexÎ°? Î∂??Ñ∞ ?í§Î°? str?ùÑ Ï∞æÎäî?ã§.
	public static int firstIndexOf(String org, String str, int fromIndex)
	{
		int index = -1;

		for (int i = fromIndex; i > -1; i--)
		{
			if (org.charAt(i) == str.charAt(0))
			{
				boolean isSame = true;

				// ?ÇòÎ®∏Ï??Î∂?Î∂ÑÏù¥ ?ùºÏπòÌïò?äî Ïß? ?ôï?ù∏
				for (int j = 1; j < str.length(); j++)
				{
					if (org.charAt(i+j) != str.charAt(j))
					{
						isSame = false;
					}
				}

				if (isSame)
				{
					index = i;
					break;
				}
			}
		}

		return index;
	}


	public static String getCurrentDate()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy?ÖÑ M?õî d?ùº a h?ãú mÎ∂?");
		String strDate = df.format(cal.getTime());

		return strDate;
	}

	public static String today() {
		return toString(Calendar.getInstance());
	}

	/**
	 * today : YYYYMMDD
	 */
	public static String addDays(String yyyymmdd, int days) {
		GregorianCalendar cal = toCalendar(yyyymmdd);
		cal.add(GregorianCalendar.DATE, days);
		return toString(cal);
	}

	public static String toString(Calendar cal) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(cal.getTime());
	}

	public static GregorianCalendar toCalendar(String yyyymmdd) {
		int yyyy 	= Integer.parseInt(yyyymmdd.substring(0,4));
		int mm		= Integer.parseInt(yyyymmdd.substring(4,6));
		int dd		= Integer.parseInt(yyyymmdd.substring(6));

		return new GregorianCalendar(yyyy, mm-1, dd,0,0,0);
	}

	public static String noData(int cnt)
	{
		if (cnt == 0) {
			return "<tr height=50 class=pad_l10><td align=center>Í≤??Éâ?êú ?ç∞?ù¥???Í∞? ?óÜ?äµ?ãà?ã§.</td></tr>";
		} else {
			return "";
		}
	}



	public static String noHTML(String html)
	{
		if (html == null || "".equals(html)) {
			return html;
		}
	    Pattern p = Pattern.compile("\\<(\\/?)(\\w+)*([^<>]*)>");
	    Matcher m = p.matcher(html);
	    return m.replaceAll("");
	}


	/**
  * replaces the pattern string in the source string with replace string
  * @author  
  * @param strSource  the string to be processed
  * @param strPat     the string pattern to be replaced
  * @param strReplace the replace string
  * @return  the newly formed replaced string else empty string
  */
	public static String  replaceAll(String strSource, String strPat, String strReplace)
	{
		int sPos = strSource.indexOf(strPat);
		StringBuffer newSource = new StringBuffer();

		while (sPos > -1)
		{
			strSource = strSource.substring(0, sPos) + strReplace + strSource.substring(sPos + strPat.length());
			sPos = strSource.indexOf(strPat);
		}

		return strSource;
	}



	public static String  replaceFirst(String strSource, String strPat, String strReplace)
	{
		int sPos = strSource.indexOf(strPat);
		StringBuffer newSource = new StringBuffer();

		if (sPos > -1)
		{
			strSource = strSource.substring(0, sPos) + strReplace + strSource.substring(sPos + strPat.length());
			sPos = strSource.indexOf(strPat);
		}

		return strSource;
	}


	public static void setPaging(HashMap infos) throws Exception {
		if (infos.get("curr_page") == null) {
			infos.put("start_num",	0);
			infos.put("end_num",	Integer.parseInt(StringUtil.nvl(infos.get("end_num"),"1")));
		}
		else {
			int currPage	= Integer.parseInt((String)infos.get("curr_page"));
			int startNum	= (currPage - 1) * LINE_PER_PAGE;
			int endNum		= LINE_PER_PAGE;

			infos.put("start_num",	new Integer(startNum));
			infos.put("end_num",	new Integer(endNum));
			//infos.put("total_count", DBManager.executeQuery(xml, sql, infos).getInt("total_count", 0));
		}
	}


	/**
     * curPage : ?òÑ?†ú?éò?ù¥Ïß?
     * totalCount : ?††Ï≤? Í≤åÏãúÎ¨ºÏàò
     * blockCount : ?ïú ?éò?ù¥Ïß??ùò Í≤åÏãúÎ¨ºÏùò ?àò
     * blockPage : ?ïú?ôîÎ©¥Ïóê Î≥¥Ïó¨Ïß? ?éò?ù¥Ïß??ùò ?àò
     * */
    public static String getPageHtml(HashMap infos, String curUrl) {

   	 	int curPage = new Integer((String)infos.get("curr_page"));	//vo.getCurPage();
   	 	int totalCount = (Integer)infos.get("total_count");	//vo.getTotCount();
   	 	int blockCount = LINE_PER_PAGE;	// vo.getPageSize();
   	 	int blockPage = PAGE_MOVE;	//vo.getSetPage();
   	 	int startCount = 0;
   	 	int endCount = 0;


   	 	StringBuffer pagingHtml = new StringBuffer();

        // ?†ÑÏ≤? ?éò?ù¥Ïß? ?àò //Math.ceil ?ò¨Î¶?
        int totalPage = (int) Math.ceil((double) totalCount / blockCount);
        if (totalPage == 0) {
            totalPage = 1;
        }

       // ?ó∞?Ç∞?óê ?ò§Î•òÍ?? ?Ç†?àò?èÑ ?ûàÍ∏∞ÎïåÎ¨∏Ïóê ?ò§Î•? Î≥¥Ï†ï
        // ?òÑ?û¨ ?éò?ù¥Ïß?Í∞? ?†ÑÏ≤? ?éò?ù¥Ïß? ?àòÎ≥¥Îã§ ?Å¨Î©? ?†ÑÏ≤? ?éò?ù¥Ïß? ?àòÎ°? ?Ñ§?†ï
        if (curPage > totalPage) {
            curPage = totalPage;
        }

       // ?òÑ?û¨ ?éò?ù¥Ïß??ùò Ï≤òÏùåÍ≥?  ÎßàÏ??Îß? Í∏??ùò Î≤àÌò∏ Í∞??†∏?ò§Í∏?
        startCount = (curPage - 1) * blockCount + 1;
        endCount = curPage * blockCount;

       // ?ãú?ûë?éò?ù¥Ïß???? ÎßàÏ??ÎßâÌéò?ù¥Ïß? Í∞? Íµ¨ÌïòÍ∏?
       int startPage = (int) ((curPage - 1) / blockPage) * blockPage + 1;
        int endPage = startPage + blockPage - 1;

       // ?ó∞?Ç∞?óê ?ò§Î•òÍ?? ?Ç†?àò?èÑ ?ûàÍ∏∞ÎïåÎ¨∏Ïóê ?ò§Î•? Î≥¥Ï†ï
        // ÎßàÏ??ÎßâÌéò?ù¥Ïß?Í∞? ?†ÑÏ≤¥Ìéò?ù¥Ïß??àòÎ≥¥Îã§ ?Å¨Î©? ?†ÑÏ≤¥Ìéò?ù¥Ïß? ?àòÎ°? ?Ñ§?†ï?ïòÍ∏?
        if (endPage > totalPage) {
            endPage = totalPage;
        }

       // ?ã§?†ú HTML?ùÑ ÎßåÎìú?äî Î∂?Î∂?
        // ?ù¥?†Ñ block ?éò?ù¥Ïß?

        pagingHtml = new StringBuffer();

        pagingHtml.append("<table border='0' cellspacing='0' cellpadding='0'>\n<tr>");
        if (curPage > blockPage) {
       	 if (curUrl.indexOf("?") > -1) {
       		 pagingHtml.append("<td class='pagenum_prev'><a href=\""+ curUrl +"&s_curr_page=" + (startPage - 1) + "\">");
       	 } else {
       		 pagingHtml.append("<td class='pagenum_prev'><a href=\""+ curUrl +"?s_curr_page=" + (startPage - 1) + "\">");
       	 }
            pagingHtml.append("<img src='/images/sub/btn_prev.gif' width='18' height='17' border='0' align='absmiddle' />");
            pagingHtml.append("</a></td>");
        }
        //pagingHtml.append("&nbsp;|&nbsp;");

       // ?éò?ù¥Ïß?Î≤àÌò∏, ?òÑ?û¨ ?éò?ù¥Ïß??äî Îπ®Í∞Ñ?Éâ?úºÎ°? Í∞ïÏ°∞?ïòÍ≥? ÎßÅÌÅ¨Î•? ?†úÍ±?.
        for (int i = startPage; i <= endPage; i++) {
            if (i > totalPage) {
                break;
            }
            if (i == curPage) {
                pagingHtml.append("<td class='pagenum_now'>");
                pagingHtml.append(i);
                pagingHtml.append("</td>");
            } else {
	        	 if (curUrl.indexOf("?") > -1) {
	        		 pagingHtml.append("<td class='pagenum'><a href=\""+ curUrl +"&s_curr_page=");
	        	 } else {
	        		 pagingHtml.append("<td class='pagenum'><a href=\""+ curUrl +"?s_curr_page=");
	        	 }
                pagingHtml.append(i);
                pagingHtml.append("\">");
                pagingHtml.append(i);
                pagingHtml.append("</a></td>");
            }
            //pagingHtml.append("&nbsp;");
        }
        //pagingHtml.append("&nbsp;&nbsp;|&nbsp;&nbsp;");

       // ?ã§?ùå block ?éò?ù¥Ïß?
        if (totalPage - startPage >= blockPage) {
       	 if (curUrl.indexOf("?") > -1) {
       		 pagingHtml.append("<td class='pagenum_next'><a href=\""+ curUrl +"&s_curr_page=" + (endPage + 1) + "\">");
       	 } else {
       		 pagingHtml.append("<td class='pagenum_next'><a href=\""+ curUrl +"?s_curr_page=" + (endPage + 1) + "\">");
       	 }
            pagingHtml.append("<img src='/images/sub/btn_next.gif' width='20' height='19' border='0' align='absmiddle' />");
            pagingHtml.append("</a></td>");
        }
        pagingHtml.append("</tr>\n</table>");

        //vo.setTotPage((int)((totalCount-1)/blockCount) + 1);

        return pagingHtml.toString();
    }




    public static String getPageMove(int aTotalPage, int aCurrentPage, String searchUrl )
    {
        StringBuffer result = new StringBuffer(100);
        if(aTotalPage == 0)
        {
            aTotalPage = 1;
        }
        if(aCurrentPage == 0)
        {
            aCurrentPage = 1;
        }
        if(aCurrentPage > aTotalPage)
        {
            aCurrentPage = aTotalPage;
        }

        int iTotalPos = (int)((aTotalPage - 1) / PAGE_MOVE) + 1;   //ÎßàÏ??Îß? ?éò?ù¥Ïß?Í∞? Ï°¥Ïû¨?ïò?äî ?úÑÏπ?
        int iCurrentPos = (int)((aCurrentPage - 1) / PAGE_MOVE) + 1;   //?òÑ?û¨ ?éò?ù¥Ïß?Í∞? Ï°¥Ïû¨?ïò?äî ?úÑÏπ?

        //?ôîÎ©¥Ïóê ?Çò????Çò?äî ?éò?ù¥Ïß? Í∞??àò
        int iViewPageCount = 1;
        if(iCurrentPos == iTotalPos)
        {
            iViewPageCount = aTotalPage % PAGE_MOVE == 0 ? PAGE_MOVE : aTotalPage % PAGE_MOVE;
        }
        else
        {
            iViewPageCount = PAGE_MOVE ;
        }

		result.append("<TD align=\"center\" class=\"gray\">\n");

        ////////////////////////////////////////////////////////////////////////
        //?ù¥?†Ñ 10?éò?ù¥Ïß?Î°? ?ù¥?èô Î≤ÑÌäº ?Ñ§?†ï
        if(iCurrentPos > 1)
        {
            int iRewPage = (iCurrentPos - 2) * PAGE_MOVE + 1;
            result.append("<a href=\"").append(searchUrl).append("&curr_page=").append(iRewPage).append("\" onfocus=\"this.blur()\" ><img name=\"ImgPageRew\"  border=\"0\" src=\"/board/img/pre_i.jpg\"  align=\"absmiddle\" alt=\"10 ?éò?ù¥Ïß? ?ù¥?†Ñ?úºÎ°? ?ù¥?èô\"></A><SPAN class=\"PageSplitHBetBtn\"></SPAN>&nbsp;&nbsp;");
        }
        else
        {
//            result.append("<IMG border=\"0\" src=\"/images/pre_i.jpg\"   align=\"absmiddle\" alt=\"10 ?éò?ù¥Ïß? ?ù¥?†Ñ?úºÎ°? ?ù¥?èô\"><SPAN class=\"PageSplitHBetBtn\"></SPAN>");
        }
        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////
        //?ù¥?†Ñ ?éò?ù¥Ïß? Î≤ÑÌäº ?Ñ§?†ï
        int iBackPage = aCurrentPage - 1;

        if(iBackPage >= 1)
        {
//            result.append("<A href=\"javascript:goList('").append(iBackPage).append("');\"  onfocus=\"this.blur()\" onMouseOut=\"document.images['ImgPagePrev'].src='/images/page_prev_1.gif' \" onMouseOver=\"document.images['ImgPagePrev'].src='/images/page_prev_2.gif' \"><IMG name=\"ImgPagePrev\" border=\"0\" src=\"/images/page_prev_1.gif\"  align=\"absmiddle\" alt=\"?ù¥?†Ñ ?éò?ù¥Ïß?Î°? ?ù¥?èô\"></A><SPAN class=\"PageSplitHBetBtnNo\"></SPAN>");
        }
        else
        {
//            result.append("<IMG  border=\"0\" src=\"/img/icon/pre_i.gif\"  align=\"absmiddle\" alt=\"?ù¥?†Ñ ?éò?ù¥Ïß?Î°? ?ù¥?èô\"><SPAN class=\"PageSplitHBetBtnNo\"></SPAN>");
        }
        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////
        //?éò?ù¥Ïß? ?à´?ûê ?ëú?ãú
        for(int i = 1 ; i <= iViewPageCount && (iCurrentPos - 1) * PAGE_MOVE + i <= aTotalPage; i++)
        {
            //?òÑ?û¨ ?éò?ù¥Ïß??ù∏ Í≤ΩÏö∞ Link ?óÜ?ùå
            if((iCurrentPos - 1) * PAGE_MOVE + i == aCurrentPage)
            {
//                result.append("<SPAN class=\"CurrPageNo\">[").append((iCurrentPos - 1) * PAGE_MOVE + i).append("]</SPAN>");
				result.append("<SPAN class=\"CurrPageNo\">").append((iCurrentPos - 1) * PAGE_MOVE + i).append("</SPAN>");
            }
            else
            {
//                result.append("<A href=\"javascript:goList('").append((iCurrentPos - 1)* PAGE_MOVE + i).append("')\"");
//                result.append(" class=\"PageNo\" onfocus=\"this.blur()\">[").append((iCurrentPos - 1) * PAGE_MOVE + i).append("]</A>");
				result.append("<a href=\"").append(searchUrl).append("&curr_page=").append((iCurrentPos - 1)* PAGE_MOVE + i).append("\"");
                result.append(" class=\"PageNo\" onfocus=\"this.blur()\">").append((iCurrentPos - 1) * PAGE_MOVE + i).append("</a>");

            }
        }//end of for loop
        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////
        //?ã§?ùå ?éò?ù¥Ïß? Î≤ÑÌäº ?Ñ§?†ï
        int iNextPage = aCurrentPage + 1;
        if(iNextPage <= aTotalPage)
        {
//            result.append("<span class=\"PageSplitHBetBtnNo\"></span>");
//            result.append("<A href=\"javascript:goList('").append(iNextPage).append("');\" onfocus=\"this.blur()\" onMouseOut=\"document.images['ImgPageNext'].src='/images/page_next_1.gif'\" onMouseOver=\"document.images['ImgPageNext'].src='/images/page_next_2.gif' \"><IMG name=\"ImgPageNext\" border=\"0\" src=\"/images/page_next_1.gif\"  align=\"absmiddle\" alt=\"?ã§?ùå ?éò?ù¥Ïß?Î°? ?ù¥?èô\"></A><SPAN class=\"PageSplitHBetBtn\"></SPAN>");
        }
        else
        {
//            result.append("<span class=\"PageSplitHBetBtnNo\"></span>");
//            result.append("<IMG border=\"0\" src=\"/images/page_next_0.gif\"  align=\"absmiddle\" alt=\"?ã§?ùå ?éò?ù¥Ïß?Î°? ?ù¥?èô\"><SPAN class=\"PageSplitHBetBtn\"></SPAN>");
        }
        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////
        //?ã§?ùå 10?éò?ù¥Ïß? ?ù¥?èô Î≤ÑÌäº ?Ñ§?†ï
        int iNextPos = iCurrentPos * PAGE_MOVE + 1;
        if(iNextPos <= aTotalPage)
        {
            result.append("&nbsp;&nbsp;<a href=\"").append(searchUrl).append("&curr_page=").append(iNextPos).append("\"  onfocus=\"this.blur()\" ><IMG name=\"ImgPageFF\" border=\"0\" src=\"/board/img/next_i.jpg\"   align=\"absmiddle\" alt=\"10 ?éò?ù¥Ïß? ?ã§?ùå?úºÎ°? ?ù¥?èô\"></a>\n");
        }
        else
        {
//            result.append("<IMG  border=\"0\" src=\"/img/icon/next_i.jpg\"  align=\"absmiddle\" alt=\"10 ?éò?ù¥Ïß? ?ã§?ùå?úºÎ°? ?ù¥?èô\">\n");
        }
	    result.append("</TD>\n");

        return result.toString();
    }


    /**
	* ?ï¥?ãπ?åå?ùº?ùÑ copy?ïò?äî Î©îÏÜå?ìú
	* @param slc => ?õêÎ≥∏Ìåå?ùºÍ≤ΩÎ°ú
	* @param tlc => Î≥µÏÇ¨?ê†?åå?ùºÍ≤ΩÎ°ú
	* @throws IOException
	*/
	public static void copyFile(File slc , File tlc) throws IOException{
		//?Üå?ä§Í∞? ?îî?†â?Ü†Î¶¨Ïù∏ Í≤ΩÏö∞
		//-->?ï¥?ãπ ?îî?†â?Ü†Î¶¨Ïùò Î™®Îì† ?åå?ùº?ùÑ ???Í≤? ?îî?†â?Ü†Î¶¨Ïóê Î≥µÏÇ¨
		if(slc.isDirectory()){
			if(!tlc.exists()){
				tlc.mkdir();
			}

			String[] children = slc.list();
			for(int i=0; i<children.length; i++){
				copyFile(new File(slc,children[i]),new File(tlc,children[i]));
			}
		}

		//?Üå?ä§Í∞? ?åå?ùº?ù∏Í≤ΩÏö∞
		else{
			InputStream in = new FileInputStream(slc);
			OutputStream out = new FileOutputStream(tlc);

			byte[] buf = new byte[1024];
			int len;
			while((len = in.read(buf)) > 0){
				out.write(buf,0,len);
			}
			in.close();
			out.close();
		}
	}


	public static String replaceContent(String content, String dir)  throws IOException
	{
		String realPath	= "/home/hosting_users/joinhawaii/www/";	//request.getRealPath("/");

		while (content.indexOf("img/temp/") > -1)
		{
			int idx = content.indexOf("img/temp/");
			String fileName = content.substring(idx+9, content.indexOf(".", idx)+4);

			content = StringUtil.replaceFirst(content, "img/temp", dir);


			File f = new File(realPath + "img/temp/" + fileName);

			StringUtil.copyFile(f, new File(realPath + dir+"/"+fileName));
			f.delete();
		}

		return content;
	}


	public static String latlng(String xml, String name, String latlng) {
		String value = "";
		try
		{

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();   //XML?ùÑ ?ã§Î£®Îäî CLASS
			DocumentBuilder parser = dbf.newDocumentBuilder();                   //XML?ùÑ ?ã§Î£®Îäî CLASS
			Document doc  = parser.parse(new StringBufferInputStream(xml));

			Element root = doc.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName(name);
			Node location = nodeList.item(0);


			NodeList locList = location.getChildNodes();
			for (int i = 0; i < locList.getLength(); i++) {
				Node node = locList.item(i);
				if (node.getNodeName() == latlng) {
					Node textNode = node.getChildNodes().item(0);
					value = textNode.getNodeValue();
					break;
				}
			}
		}
		catch (Exception e) {

		}

		return value;
	}

	
	public static String makeOption(List list, String selectedValue) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			sb.append("<option value=\""+((Map)list.get(i)).get("code")+"\" ");
			if (((Map)list.get(i)).get("code").equals(selectedValue)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >"+((Map)list.get(i)).get("code_name")+"</option>");
		}

		return sb.toString();
	}

	public static String makeOption(List codelist, String opt_key_name, String opt_value_name, String selectedValue) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < codelist.size(); i++) {
			sb.append("<option value=\""+((Map)codelist.get(i)).get(opt_key_name)+"\" ");
			if (((Map)codelist.get(i)).get(opt_key_name).equals(selectedValue)) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(" >"+((Map)codelist.get(i)).get(opt_value_name)+"</option>");
		}

		return sb.toString();
	}
	
	public static List makeOption(List mapList, List coeList, String mapListKey, String opt_key_name, String opt_value_name){
		
		StringBuffer sb = new StringBuffer();
		if (mapList != null && mapList.size() > 0){
			for(int i=0; i < mapList.size(); i++){
				((Map)mapList.get(i)).put(mapListKey, makeOption(coeList, opt_key_name, opt_value_name, (String)((Map)mapList.get(i)).get(mapListKey)));
			}
		} else {
			mapList = new ArrayList();
			Map map = new HashMap();
			map.put("prod_cd", makeOption(coeList, opt_key_name, opt_value_name, ""));
			mapList.add(map);
		}
		
		return mapList;
	}
	
	public static String lpad(String str, int len, String addStr) {
        String result = str;
        int templen   = len - result.length();

        for (int i = 0; i < templen; i++){
              result = addStr + result;
        }

        return result;
    }
	
	public static int getMaxDay(String dateStr){
		if (dateStr.length() != 8){
			return 0;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.set(parseInt(dateStr.substring(0, 4)), parseInt(dateStr.substring(4, 6))-1, parseInt(dateStr.substring(6, 8)));
			return cal.getActualMaximum(cal.DAY_OF_MONTH);
		}
	}
	
	public static int getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.YEAR);
	}
	
	public static String getStrCurrentYear(){
		return String.valueOf(getCurrentYear());
	}
	
	public static int getCurrentMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.MONTH)+1;
	}

	public static String getStrCurrentMonth(){
		return lpad(String.valueOf(getCurrentMonth()),2,"0");
	}
	
	public static void main(String[] args){

	}
}
