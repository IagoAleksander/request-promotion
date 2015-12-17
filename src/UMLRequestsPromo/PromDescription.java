package UMLRequestsPromo;

import java.util.Date;

public class PromDescription {
	
	private String type;
	private double cost;
	private Date endDate;
	private String details;
	
	public PromDescription(String type, double cost, Date endDate, String details)
	{
		this.type = type;
		this.cost = cost;
		this.endDate = endDate;
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
