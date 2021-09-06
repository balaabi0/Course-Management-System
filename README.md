# Course-Management-System

Course:
{
	Id: number,
	Course_name: string,
	Description: string,
	Lecturer_id: number,
}


Student:
{
	Id: number,
	Name: string,
	Email: string,
	Phone: number,
	Password: string
}

Lecturer:
{
	Id: number,
	Name: string,
	Email: string,
	Phone: number,
	Password: string
}

Subscription:
{
	Id: number,
	Course_id: number,
	Student_id: number
}


-r1) Get course info API: GET /course/{course_id}

r2) Store course info API: POST /course
	POST /course?lecturer_id=

r8) Retrive contact info of stud/lect of subs course API: GET /course/{course_id}/subscribed_contacts
	GET /course/{course_id}/students_contacts
	GET /course/{course_id}/lecturer_contacts

-r11) Subscribe/Unsubscribe to course API: PUT /course/{course_id}/subscribe?student_id=''&subscribe=[0,1]

r19) Search API: GET /course/search?searchterm=

-r21) Edit stud personal info API: PUT /students/{id} 

r22) change stud pass API: POST /students/{student_id}/change_password
	PUT /students/{student_id}/change_password



