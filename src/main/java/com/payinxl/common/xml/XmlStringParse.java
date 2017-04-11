package com.payinxl.common.xml;

/**
 * @类功能说明：非常山寨的一个XML解析类，只适用于解析简单的无重名TAG的XML
 * @公司名称：Noname
 * @作者：Panda
 * @创建时间：2012-12-18 下午10:24:41
 * @版本：V1.0
 */
public class XmlStringParse
{
    private String src;

    public String getSrc()
    {
        return src;
    }

    public void setSrc(String src)
    {
        this.src = src;
    }

    public XmlStringParse(String src)
    {
        this.src = src;
    }

    public String getParameter(String name)
    {
        String tempstr = src;
        if (tempstr == null)
        {
            return "";
        }
        int si = tempstr.indexOf("<" + name + ">");
        if (si == -1)
        {
            return "";
        }
        else
        {
            tempstr = tempstr.substring(si + name.length() + 2);
        }
        int ei = tempstr.indexOf("</" + name + ">");
        String ret = tempstr.substring(0, ei);
        if (ret == null)
        {
            return "";
        }
        else
        {
            return ret;
        }
    }

    public static void main(String args[])
    {
        XmlStringParse xml = new XmlStringParse("<?xml version=\"1.0\" encoding=\"GB2312\"?><fill version=\"1.0\"><items><item name=\"state\" value=\"0\"></items></fill>");
        System.out.println(xml.getParameter("orderinfo") + "|");
        System.out.println("|" + xml.getParameter("err_msg") + "|");
        System.out.println(xml.getParameter("game_state") + "|");
        System.out.println(xml.getParameter("game_area") + "|");
        System.out.println(xml.getParameter("retcode") + "|");

    }
}