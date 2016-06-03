package servlet;

import java.util.List;

import bean.AdminRegion;
import bean.Data;
import bean.DataDisplay;
import bean.Line;
import bean.Organization;
import bean.Pole;

public class Result

{

	private int result;


	private List<Organization>organizations;
	private List<AdminRegion>adminRegions;
	private List<Line>lines;
	private List<Pole>poles;
	private List<Data>datas;
	private List<DataDisplay>dataDisplays;


	public List<DataDisplay> getDataDisplays()
	{
		return dataDisplays;
	}


	public void setDataDisplays(List<DataDisplay> dataDisplays)
	{
		this.dataDisplays = dataDisplays;
	}


	public List<Line> getLines()
	{
		return lines;
	}


	public void setLines(List<Line> lines)
	{
		this.lines = lines;
	}


	public List<Pole> getPoles()
	{
		return poles;
	}


	public void setPoles(List<Pole> poles)
	{
		this.poles = poles;
	}


	public List<Data> getDatas()
	{
		return datas;
	}


	public void setDatas(List<Data> datas)
	{
		this.datas = datas;
	}


	public List<AdminRegion> getAdminRegions()
	{
		return adminRegions;
	}


	public void setAdminRegions(List<AdminRegion> adminRegions)
	{
		this.adminRegions = adminRegions;
	}


	public int getResult()
	{
		return result;
	}


	public void setResult(int result)
	{
		this.result = result;
	}


	public List<Organization> getOrganizations()
	{
		return organizations;
	}


	public void setOrganizations(List<Organization> organizations)
	{
		this.organizations = organizations;
	}






}
