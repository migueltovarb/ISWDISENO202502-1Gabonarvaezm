package paqueteEmployee;

public class PrgramaEmployee{
    public static void main(String[] args) {
        // Crear empleado
        Employee emp1 = new Employee(101, "Carlos", "Ramirez", 2500);
        
        // Mostrar información del empleado
        System.out.println(emp1);

        // Mostrar salario mensual y anual
        System.out.println("Salario mensual: " + emp1.getSalary());
        System.out.println("Salario anual: " + emp1.getAnnualSalary());

        // Aumentar salario en 10%
        emp1.raiseSalary(10);
        System.out.println("Salario tras aumento del 10%: " + emp1.getSalary());

        // Cambiar salario manualmente
        emp1.setSalary(4000);
        System.out.println("Nuevo salario fijo: " + emp1.getSalary());

        // Ver información actualizada
        System.out.println(emp1);
    }
}
