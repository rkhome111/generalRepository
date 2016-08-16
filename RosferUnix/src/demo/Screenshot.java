package demo;
import java.io.Serializable;
import java.rmi.Remote;
import java.sql.*;
public class Screenshot implements Serializable,Remote
{
	
	private int id,user_id,project_id,task_list_id,task_id,tl_id,created_by_id,updated_by_id,verified_by_id,record_status,work_status,work_seconds;
	private String reference_id,task_text,description,comment,sys_name,status;
	private String time_slot;
	private String actual_created_on,created_on,updated_on,verified_on;
	private String work_date,version;
	
	private int teamId,projectTlId,projectTeamId;
	
	
	
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getProjectTlId() {
		return projectTlId;
	}
	public void setProjectTlId(int projectTlId) {
		this.projectTlId = projectTlId;
	}
	public int getProjectTeamId() {
		return projectTeamId;
	}
	public void setProjectTeamId(int projectTeamId) {
		this.projectTeamId = projectTeamId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public int getTask_list_id() {
		return task_list_id;
	}
	public void setTask_list_id(int task_list_id) {
		this.task_list_id = task_list_id;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public int getTl_id() {
		return tl_id;
	}
	public void setTl_id(int tl_id) {
		this.tl_id = tl_id;
	}
	public int getCreated_by_id() {
		return created_by_id;
	}
	public void setCreated_by_id(int created_by_id) {
		this.created_by_id = created_by_id;
	}
	public int getUpdated_by_id() {
		return updated_by_id;
	}
	public void setUpdated_by_id(int updated_by_id) {
		this.updated_by_id = updated_by_id;
	}
	public int getVerified_by_id() {
		return verified_by_id;
	}
	public void setVerified_by_id(int verified_by_id) {
		this.verified_by_id = verified_by_id;
	}
	public int getRecord_status() {
		return record_status;
	}
	public void setRecord_status(int record_status) {
		this.record_status = record_status;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getWork_status() {
		return work_status;
	}
	public void setWork_status(int work_status) {
		this.work_status = work_status;
	}
	public int getWork_seconds() {
		return work_seconds;
	}
	public void setWork_seconds(int work_seconds) {
		this.work_seconds = work_seconds;
	}
	public String getReference_id() {
		return reference_id;
	}
	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}
	public String getTask_text() {
		return task_text;
	}
	public void setTask_text(String task_text) {
		this.task_text = task_text;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSys_name() {
		return sys_name;
	}
	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
	
	public String getTime_slot() {
		return time_slot;
	}
	public void setTime_slot(String time_slot) {
		this.time_slot = time_slot;
	}
	public String getActual_created_on() {
		return actual_created_on;
	}
	public void setActual_created_on(String actual_created_on) {
		this.actual_created_on = actual_created_on;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public String getUpdated_on() {
		return updated_on;
	}
	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
	}
	public String getVerified_on() {
		return verified_on;
	}
	public void setVerified_on(String verified_on) {
		this.verified_on = verified_on;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	@Override
	public String toString() {
		return "Screenshot [id=" + id + ", user_id=" + user_id + ", project_id=" + project_id + ", task_list_id="
				+ task_list_id + ", task_id=" + task_id + ", tl_id=" + tl_id + ", created_by_id=" + created_by_id
				+ ", updated_by_id=" + updated_by_id + ", verified_by_id=" + verified_by_id + ", record_status="
				+ record_status + ", work_status=" + work_status + ", work_seconds=" + work_seconds + ", reference_id="
				+ reference_id + ", task_text=" + task_text + ", description=" + description + ", comment=" + comment
				+ ", sys_name=" + sys_name + ", status=" + status + ", time_slot=" + time_slot + ", actual_created_on="
				+ actual_created_on + ", created_on=" + created_on + ", updated_on=" + updated_on + ", verified_on="
				+ verified_on + ", work_date=" + work_date + ", version=" + version + "]";
	}

	

	}
