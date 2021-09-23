package spring_course.spring_boot_rest_app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import spring_course.spring_boot_rest_app.entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    // В прил. без BOOT, мы писали @Bean public LocalSessionFactoryBean sessionFactory() в конфигфайле и затем здесь, в репозитории EmployeeDAOImpl доставали с помощью DI (@Autowired)
    // private SessionFactory sessionFactory; // а также использовали hibernate import org.hibernate.Session и import org.hibernate.SessionFactory;
    // Здесь же мы используем EntityManager без создания бина, Spring Boot автоматически создает его за кулисами, мы только используя DI (@Autowired) используем EntityManager
    // А также используем JPA (JPA (Java Persistence API) вместо Hibernate
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Employee> getAllEmployees() {
//       import org.hibernate.Session; import org.hibernate.SessionFactory; import org.hibernate.query.Query;
//        Session session = sessionFactory.getCurrentSession();

//       import org.hibernate.Session; import org.hibernate.query.Query;
//       Session session = entityManager.unwrap(Session.class); //entityManager - обертка Session, для получения из него сессии нужен метод .unwrap (развернуть обертку)
//       Query<Employee> query = session.createQuery("from Employee", Employee.class);//from Employee используем имя класса, не имя таблицы, from подчеркнут красным, не обращать внимания, не ошибка

//javax.persistence.Query is without generics as opposed to org.hibernate.query.Query, where Query<?>
        Query query = entityManager.createQuery("from Employee");
        List<Employee> allEmployees = query.getResultList();

        return allEmployees;
    }

    @Override
    public void saveEmployee(Employee employee) {
//        Session session = entityManager.unwrap(Session.class);
//        session.saveOrUpdate(employee);//если только .save(), то новый сотрудник добавится без id, поэтому .saveOrUpdate
        Employee newEmployee=entityManager.merge(employee); //в браузере id нового работника =0, в то время как в БД формируется корректный номер ID, чтобы видеть его в браузере устанавливаем его номер set
        employee.setId(newEmployee.getId());
    }

    @Override
    public Employee getEmployee(int id) {
//        Session session = entityManager.unwrap(Session.class);
//        Employee employee = session.get(Employee.class, id);
        Employee employee = entityManager.find(Employee.class, id);
        return employee;
    }

    @Override
    public void deleteEmployee(int id) {
//        Session session = entityManager.unwrap(Session.class);
//        Query<Employee> query = session.createQuery("delete from Employee where id=:employeeId"); //:employeeId - мы говорим, что вместо employeeId мы пропишем параметр
//        query.setParameter("employeeId", id);//замена названия параметра на его значение, employeeId в этом запросе будет заменяться на id работника, который прописывается в параметре метода .deleteEmployee(int id)
//        query.executeUpdate();

        Query query = entityManager.createQuery("delete from Employee where id=:employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();


    }
}
