<template>
  <a-row>
    <a-col :span="24">
      <a-card>
        <a-row>
          <a-col :span="8">
            <a-statistic title="总阅读量" :value="statistic.viewCount">
              <template #suffix>
                <UserOutlined />
              </template>
            </a-statistic>
          </a-col>
          <a-col :span="8">
            <a-statistic title="总点赞量" :value="statistic.voteCount">
              <template #suffix>
                <like-outlined />
              </template>
            </a-statistic>
          </a-col>
          <a-col :span="8">
            <a-statistic title="点赞率" :value="statistic.voteCount / statistic.viewCount *100"
                         :precision="2"
                         suffix="%"
                         :value-style="{ color: '#e00d1e' }">
              <template #suffix>
                <like-outlined />
              </template>
            </a-statistic>
          </a-col>
        </a-row>
      </a-card>
    </a-col>
  </a-row>
  <br>
  <a-row :gutter="16">
    <a-col :span="12">
      <a-card>
        <a-row>
          <a-col :span="12">
            <a-statistic
                title="今日阅读"
                :value="statistic.todayViewCount" style="margin-right: 50px"
                :value-style="{ color: '#98ee4c' }"
            >
              <template #suffix>
                <UserOutlined />
              </template>
            </a-statistic>
          </a-col>
          <a-col :span="12">
            <a-statistic
                title="今日点赞"
                :value="statistic.todayVoteCount"
            >
              <template #suffix>
                <UserOutlined />
              </template>
            </a-statistic>
          </a-col>
        </a-row>
      </a-card>
    </a-col>
    <a-col :span="12">
      <a-card>
        <a-row>
          <a-col :span="12">
            <a-statistic
                title="预计今日阅读"
                :value="statistic.todayViewIncrease"
                :value-style="{ color: '#0000ff' }"
            >
              <template #suffix>
                <UserOutlined />
              </template>
            </a-statistic>
          </a-col>
          <a-col :span="12">
            <a-statistic
                title="预计今日阅读增长"
                :precision="2"
                suffix="%"
                :value="statistic.todayViewIncreaseRateAbs"
                :value-style="statistic.todayViewIncreaseRate < 0 ? { color: '#3f8600' } : { color: '#cf1322' }"
                class="demo-class"
            >

            </a-statistic>
          </a-col>
        </a-row>
      </a-card>
    </a-col>
  </a-row>
  <br>
  <a-row>
    <a-col :span="24">
      <div id="main" style="width: calc(100% - 200px);height: 300px;"></div>
    </a-col>
  </a-row>
</template>

<script lang="ts">
import {computed, defineComponent, onMounted, ref} from 'vue';
import axios from "axios";

declare let echarts :any;
export default defineComponent({
    name: 'the-welcome',
    setup() {
      const statistic = ref();
      statistic.value = {};
      const getStatistic = () => {
        axios.get('/ebook-snapshot/get-statistic').then((response)=>{
          const data = response.data;
          if(data.success){
            const statisticResp = data.content;
            if (statisticResp.length==1){
              statistic.value.viewCount = statisticResp[0].viewCount;
              statistic.value.voteCount = statisticResp[0].voteCount;
              statistic.value.todayVoteCount = statisticResp[0].voteIncrease;
              statistic.value.todayViewCount = statisticResp[0].viewIncrease;

              //按分钟计算当前的时间点，占一天的百分比
              const now = new Date();
              const nowRate = (now.getHours() * 60 + now.getMinutes()) / (60*24);

              statistic.value.todayViewIncrease = parseInt(String(statisticResp[0].viewIncrease / nowRate));
              //今日预计增长率=（今日预计-昨天增长）/昨天增长
              statistic.value.todayViewIncreaseRate = 0
              statistic.value.todayViewIncreaseRateAbs = 0;
            }else{
              statistic.value.viewCount = statisticResp[1].viewCount;
              statistic.value.voteCount = statisticResp[1].voteCount;
              statistic.value.todayVoteCount = statisticResp[1].voteIncrease;
              statistic.value.todayViewCount = statisticResp[1].viewIncrease;

              //按分钟计算当前的时间点，占一天的百分比
              const now = new Date();
              const nowRate = (now.getHours() * 60 + now.getMinutes()) / (60*24);

              statistic.value.todayViewIncrease = parseInt(String(statisticResp[1].viewIncrease / nowRate));
              //今日预计增长率=（今日预计-昨天增长）/昨天增长
              statistic.value.todayViewIncreaseRate = (statistic.value.todayViewIncrease - statisticResp[0].viewIncrease) / statisticResp[0].viewIncrease * 100;
              statistic.value.todayViewIncreaseRateAbs = Math.abs(statistic.value.todayViewIncreaseRate);
            }

          }
        });
      };

      const testEcharts = () => {
        // 基于准备好的dom，初始化echarts实例
        const myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        const option = {
          title: {
            text: 'ECharts 入门示例'
          },
          tooltip: {},
          legend: {
            data: ['销量']
          },
          xAxis: {
            data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
          },
          yAxis: {},
          series: [
            {
              name: '销量',
              type: 'bar',
              data: [5, 20, 36, 10, 10, 20]
            }
          ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      }


      const init30DayEcharts = (list: any) => {
        // 基于准备好的dom，初始化echarts实例
        const myChart = echarts.init(document.getElementById('main'));

        const xAxis = [];
        const seriesView = [];
        const seriesVote = [];
        for(let i = 0;i<list.length;i++){
          const record = list[i];
          xAxis.push(record.date);
          seriesView.push(record.viewIncrease);
          seriesVote.push(record.voteIncrease);
        }
        const option = {
          title: {
            text: '30天趋势图'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['总阅读量', '总点赞量']
          },
          grid: {
            left: '1%',
            right: '3%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '总阅读量',
              type: 'line',
              stack: 'Total',
              data: seriesView,
              smooth: true
            },
            {
              name: '总点赞量',
              type: 'line',
              stack: 'Total',
              data: seriesVote,
              smooth: true
            }
          ]
        };
        //使用刚指定的配置项和数据显示图表
        myChart.setOption(option);
      };


      const get30DayStatistic = () => {
        axios.get('/ebook-snapshot/get-30-statistic').then((response)=>{
          const data = response.data;
          if(data.success){
            const statisticList = data.content;

            init30DayEcharts(statisticList);
          }
        });
      };


      onMounted(() => {
        getStatistic();
        // testEcharts();
        get30DayStatistic();
      });
      return {
        statistic,
      }
    }
  });
</script>
