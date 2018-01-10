package p.minn.flowchart;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import p.minn.workflow.entity.ProcessDefinition;
import p.minn.workflow.utils.GojsTransform;
/**
 * 
 * @author minn 
 * @QQ:3942986006
 * @comment 
 * 
 */
public class FlowChartModelTest {

	
	@Before
	public void setUp() throws Exception {
		
	}

	//@Test
	public void easyTest() {
		String modealData="{ \"class\": \"go.GraphLinksModel\",\n" + 
				"  \"linkFromPortIdProperty\": \"fromPort\",\n" + 
				"  \"linkToPortIdProperty\": \"toPort\",\n" + 
				"  \"modelData\": {\"position\":\"15 124\"},\n" + 
				"  \"nodeDataArray\": [ \n" + 
				"{\"code\":\"N006\", \"gid\":338, \"children\":[], \"pid\":-1, \"id\":7, \"sort\":\"6\", \"text\":\"请假环节\", \"state\":{\"selected\":false}, \"url\":\"node6\", \"selected\":false, \"key\":\"7@N006@\", \"loc\":\"170 170\"},\n" + 
				"{\"code\":\"D001\", \"children\":[], \"pid\":-1, \"id\":1, \"text\":\"人力资源部\", \"sort\":1, \"state\":{\"selected\":false}, \"rid\":1, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"1@D001@\", \"loc\":\"220 260\"},\n" + 
				"{\"code\":\"D003\", \"children\":[], \"pid\":-1, \"id\":3, \"text\":\"财务部\", \"sort\":3, \"state\":{\"selected\":false}, \"rid\":3, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"3@D003@\", \"loc\":\"220 350\"},\n" + 
				"{\"code\":\"D001\", \"children\":[], \"pid\":-1, \"id\":1, \"text\":\"人力资源部\", \"sort\":1, \"state\":{\"selected\":false}, \"rid\":1, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"1@D001@2\", \"loc\":\"190 440\"}\n" + 
				" ],\n" + 
				"  \"linkDataArray\": [ \n" + 
				"{\"from\":\"1@D001@2\", \"to\":\"3@D003@\", \"fromPort\":\"R\", \"toPort\":\"\", \"points\":[235.64997100830078,440,245.64997100830078,440,245.64997100830078,396,220,396,220,376.9377243041992,220,366.9377243041992]},\n" + 
				"{\"from\":\"3@D003@\", \"to\":\"1@D001@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[220,333.0622756958008,220,323.0622756958008,220,305,220,305,220,286.9377243041992,220,276.9377243041992]},\n" + 
				"{\"from\":\"1@D001@\", \"to\":\"7@N006@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[220,243.0622756958008,220,233.0622756958008,220,215,170,215,170,196.93772430419924,170,186.93772430419924]}\n" + 
				" ]}";
		
		List<ProcessDefinition> rs=GojsTransform.transform(1,"108_1", modealData);
		
		System.out.println("insert size:"+rs.size());
		for(ProcessDefinition pd:rs) {
			System.out.println("pid:"+pd.getPId()+",id:"+pd.getId()+",name:"+pd.getName());
		}
	}
	
	@Test
	public void complexTest() {
		String modealData="{ \"class\": \"go.GraphLinksModel\",\n" + 
				"  \"linkFromPortIdProperty\": \"fromPort\",\n" + 
				"  \"linkToPortIdProperty\": \"toPort\",\n" + 
				"  \"modelData\": {\"position\":\"-5 -5\"},\n" + 
				"  \"nodeDataArray\": [ \n" + 
				"{\"code\":\"N001\", \"gid\":328, \"children\":[], \"pid\":-1, \"id\":1, \"sort\":\"1\", \"text\":\"环节1\", \"state\":{\"selected\":false}, \"url\":\"node1\", \"selected\":false, \"key\":\"1@N001@\", \"loc\":\"130 140\"},\n" + 
				"{\"code\":\"D001\", \"children\":[], \"pid\":-1, \"id\":1, \"text\":\"人力资源部\", \"sort\":1, \"state\":{\"selected\":false}, \"rid\":1, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"1@D001@\", \"loc\":\"140 230\"},\n" + 
				"{\"code\":\"D003\", \"children\":[], \"pid\":-1, \"id\":3, \"text\":\"财务部\", \"sort\":3, \"state\":{\"selected\":false}, \"rid\":3, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"3@D003@\", \"loc\":\"130 310\"},\n" + 
				"{\"code\":\"D001\", \"children\":[], \"pid\":-1, \"id\":1, \"text\":\"人力资源部\", \"sort\":1, \"state\":{\"selected\":false}, \"rid\":1, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"1@D001@2\", \"loc\":\"120 420\"},\n" + 
				"{\"code\":\"N002\", \"gid\":329, \"children\":[], \"pid\":-1, \"id\":2, \"sort\":\"2\", \"text\":\"环节2\", \"state\":{\"selected\":false}, \"url\":\"node2\", \"selected\":false, \"key\":\"2@N002@\", \"loc\":\"390 140\"},\n" + 
				"{\"code\":\"D003\", \"children\":[], \"pid\":-1, \"id\":3, \"text\":\"财务部\", \"sort\":3, \"state\":{\"selected\":false}, \"rid\":3, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"3@D003@2\", \"loc\":\"310 230\"},\n" + 
				"{\"code\":\"D004\", \"children\":[], \"pid\":-1, \"id\":4, \"text\":\"商务部\", \"sort\":4, \"state\":{\"selected\":false}, \"rid\":4, \"rpid\":-1, \"type\":\"department\", \"selected\":false, \"key\":\"4@D004@\", \"loc\":\"390 340\"}\n" + 
				" ],\n" + 
				"  \"linkDataArray\": [ \n" + 
				"{\"from\":\"1@D001@2\", \"to\":\"3@D003@\", \"fromPort\":\"R\", \"toPort\":\"\", \"points\":[165.64997100830078,420,175.64997100830078,420,175.64997100830078,366,130,366,130,336.9377243041992,130,326.9377243041992]},\n" + 
				"{\"from\":\"3@D003@\", \"to\":\"1@D001@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[130,293.0622756958008,130,283.0622756958008,130,270,140,270,140,256.93772430419926,140,246.93772430419924]},\n" + 
				"{\"from\":\"1@D001@\", \"to\":\"1@N001@\", \"fromPort\":\"\", \"toPort\":\"R\", \"points\":[140,213.0622756958008,140,203.0622756958008,140,184,167.73657989501953,184,167.73657989501953,140,157.73657989501953,140]},\n" + 
				"{\"from\":\"4@D004@\", \"to\":\"3@D003@2\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[390,323.0622756958008,390,313.0622756958008,390,285,310,285,310,256.93772430419926,310,246.93772430419924]},\n" + 
				"{\"from\":\"3@D003@2\", \"to\":\"2@N002@\", \"fromPort\":\"\", \"toPort\":\"\", \"points\":[310,213.0622756958008,310,203.0622756958008,310,185,390,185,390,166.9377243041992,390,156.9377243041992]},\n" + 
				"{\"from\":\"1@N001@\", \"to\":\"2@N002@\", \"fromPort\":\"\", \"toPort\":\"L\", \"points\":[157.73657989501953,140,167.73657989501953,140,260,140,260,140,352.26342010498047,140,362.26342010498047,140]}\n" + 
				" ]}";
		List<ProcessDefinition> rs=GojsTransform.transform(1,"108_2", modealData);
		
		System.out.println("insert size:"+rs.size());
		for(ProcessDefinition pd:rs) {
			System.out.println("pid:"+pd.getPId()+",id:"+pd.getId()+",code:"+pd.getCode()+",name:"+pd.getName());
		}

	}
	
	
}
