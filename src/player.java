

public class player {
	public String name;
	public int saldo;
	
	public void setName(String pname) {
		name=pname;
	}
	
	public void setSaldo(int psaldo) {
		saldo=psaldo;
	}
	
	
	public String getName() {
		return name;
	}
	public int getSaldo() {
		return saldo;
	}
	@Override
	public String toString() {
		return "player [name=" + name + ", saldo=" + saldo + ", getName()=" + getName() + ", getSaldo()=" + getSaldo()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	

}
