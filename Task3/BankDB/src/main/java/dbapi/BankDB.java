package dbapi;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.GregorianCalendar;

enum FailureCause{BADHISTORY, BIGMONEY, TERROR, NONE}

public class BankDB {
	private final static String JDBC_DRIVER="com.mysql.jdbc.Driver";
	private final static String URL="jdbc:mysql://localhost/bankdb";
	private final static String USER_NAME="root";
	private final static String PASSWORD="root";
	private final static String SELECT_CLIENTS_STRING=	"SELECT "+
														"id_req, clients.id_client, name, lastname, patronymic, result, failure_cause "+
														"FROM "+
														"clients, requests, clientpartner "+
														"WHERE "+
														"requests.id_cl_part=clientpartner.id_cl_part AND "+
														"clientpartner.id_client=clients.id_client AND "+
														"result='NO' AND "+
														"start_time> ? AND "+
														"terminate_time< ? "+
														"GROUP BY name, lastname, patronymic, failure_cause";

	
	public static void main(String[] args) throws Exception {
		Date beginDate=new Date(new GregorianCalendar(2017,Calendar.SEPTEMBER,15,0,0,0).getTime().getTime());
		Date endDate=new Date(new GregorianCalendar(2017,Calendar.SEPTEMBER,21,0,0,0).getTime().getTime());
		BankDB bd= new BankDB();

		EnumMap<FailureCause, ArrayList<Client>> clientsEnumMap=bd.getClientsMap(beginDate,endDate);
		System.out.println(clientsEnumMap);


	}
	
	public Connection getConnection() throws SQLException{
		System.setProperty("jdbc.drivers",JDBC_DRIVER);
		return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		
	}
	
	
	public EnumMap<FailureCause, ArrayList<Client>> getClientsMap(Date beginDate, Date endDate) throws SQLException{
		EnumMap<FailureCause, ArrayList<Client>> clientsEnumMap = new EnumMap<>(FailureCause.class);
		
		for(FailureCause f:FailureCause.values())
			clientsEnumMap.put(f, new ArrayList<Client>());
		
		try(Connection conn=getConnection()){
			try(PreparedStatement st=conn.prepareStatement(SELECT_CLIENTS_STRING)){
				st.setDate(1, beginDate);
				st.setDate(2, endDate);
				try(ResultSet res= st.executeQuery()){
					while(res.next()){
						FailureCause fc=FailureCause.valueOf(res.getString("failure_cause"));
						Client client=new Client(res.getInt("id_client"),res.getString("name"),res.getString("lastname"),res.getString("patronymic"));
						clientsEnumMap.get(fc).add(client);
					}
				}
			}
		}
		return clientsEnumMap;

	}

}
