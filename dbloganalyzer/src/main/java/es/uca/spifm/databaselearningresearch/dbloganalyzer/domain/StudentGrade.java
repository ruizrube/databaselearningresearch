/**
 * 
 */
package es.uca.spifm.databaselearningresearch.dbloganalyzer.domain;

/**
 * @author ivanruizrube
 *
 */
public class StudentGrade {

	private String userId;
	private Double theoryGrade;
	private Double practiceGrade;


	public StudentGrade() {
		// TODO Auto-generated constructor stub
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Double getTheoryGrade() {
		return theoryGrade;
	}


	public void setTheoryGrade(Double theoryGrade) {
		this.theoryGrade = theoryGrade;
	}


	public Double getPracticeGrade() {
		return practiceGrade;
	}


	public void setPracticeGrade(Double practiceGrade) {
		this.practiceGrade = practiceGrade;
	}

	

}
