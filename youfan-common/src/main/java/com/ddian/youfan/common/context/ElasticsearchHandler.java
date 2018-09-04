/*
package com.ddian.youfan.common.context;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

*/
/**
 * ES 操作工具类.
 *
 * @Author: WenheZhu
 * @Description:
 * @Date: Created in 10:042018/6/12
 * @Modified By:
 *//*

@SuppressWarnings("all")
public class ElasticsearchHandler {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchHandler.class);
    private TransportClient client;

    public ElasticsearchHandler(String ip, int port, String cluster, boolean sniff) {
        try {
            Settings esSettings = Settings.builder()
                    .put("cluster.name", cluster) //设置ES实例的名称
                    .put("client.transport.sniff", sniff) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                    .build();

             */
/*
             * 这里的连接方式指的是没有安装x-pack插件,如果安装了x-pack则参考{@link ElasticsearchXPackClient}
             * 1. java客户端的方式是以tcp协议在9300端口上进行通信
             * 2. http客户端的方式是以http协议在9200端口上进行通信
             *//*

            client = new PreBuiltTransportClient(esSettings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
            logger.info("[ES] client load the success !");
        } catch (Exception e) {

        }
    }


    public Client getClient() {
        return this.client;
    }


    */
/**
     * 检查索引是否存在
     *
     * @param INDEX
     * @return
     *//*

    public boolean indexExists(String index) {
        if (StringUtils.isEmpty(index)) {
            logger.info("[ES] index is Empty !");
            return false;
        }
        return ((IndicesExistsResponse) this.client.admin().indices().exists(Requests.indicesExistsRequest(new String[]{index})).actionGet()).isExists();
    }


    */
/**
     * 删除索引
     *
     * @param INDEX
     * @return
     *//*

    public boolean deleteIndex(String index) {
        if (StringUtils.isEmpty(index)) {
            logger.info("[ES] index is Empty !");
            return false;
        }
        return this.indexExists(index) ? ((DeleteIndexResponse) this.client.admin().indices().delete(new DeleteIndexRequest(index)).actionGet()).isAcknowledged() : false;
    }

    */
/**
     * 存储多条检索信息
     *
     * @param index
     * @param type
     * @param primaryKey
     * @param list
     *//*

    public void push(String index, String type, String primaryKey, List<Map<String, Object>> list) {
        try {
            if (StringUtils.isEmpty(index) || StringUtils.isEmpty(type) || StringUtils.isEmpty(primaryKey) || list == null || list.isEmpty()) {
                logger.info("[ES] parameter is Empty !");
                return;
            }
            BulkRequestBuilder bulkRequest = this.client.prepareBulk();
            for (Map<String, Object> map : list) {
                XContentBuilder xb = XContentFactory.jsonBuilder().startObject();
                for (Object key : map.keySet()) {
                    xb.field((String) key, map.get(key));
                }
                xb.endObject();
                bulkRequest.add(client.prepareIndex(index, type, String.valueOf(map.get(primaryKey)).trim()).setSource(xb));
            }
            BulkResponse bulkResponse = bulkRequest.get();
            if (bulkResponse.hasFailures()) {
                logger.info("[ES] push data failure !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("[ES] push data success !");
    }


    public void update(String index, String type, String id, String key, String value) {
        try {
            if (StringUtils.isEmpty(index) || StringUtils.isEmpty(type) || StringUtils.isEmpty(id) || StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
                logger.info("[ES] parameter is Empty !");
                return;
            }
            UpdateRequest updateRequest = new UpdateRequest(index, type, id);
            updateRequest.doc(XContentFactory.jsonBuilder().startObject().field(key, value).endObject());
            this.client.update(updateRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    */
/**
     * 查询服务
     *
     * @param INDEX
     * @param type
     * @param qb
     * @return
     *//*

    public SearchResponse query(String index, String type, QueryBuilder qb) {
        try {
            if (StringUtils.isEmpty(index) || StringUtils.isEmpty(type) || qb == null) {
                logger.info("[ES] parameter is Empty !");
                return null;
            }
            if (this.indexExists(index)) {
                SearchResponse response = client.prepareSearch(index)
                        .setTypes(type)
                        .setQuery(qb)
                        .get();
                return response;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    */
/**
     * 获取单行数据
     *
     * @param index
     * @param type
     * @param id
     * @return
     *//*

    public Map<String, Object> getOne(String index, String type, String id) {
        GetResponse response = client.prepareGet(index, type, id)
                .setOperationThreaded(false)    // 线程安全
                .get();
        return response.getSource();
    }

    public static void main(String[] args) {
        ElasticsearchHandler eh = new ElasticsearchHandler("192.168.1.64", 9300, "es-cluster", true);
        QueryBuilder qb = QueryBuilders.matchPhraseQuery("name","朱文赫");
        SearchResponse sr =  eh.query("sys_user","demo",qb);
        System.out.println(sr);
    }
}
*/
