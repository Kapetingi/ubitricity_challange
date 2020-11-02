<template>
  <div class="service">
    <h1>Carpark Ubi Report</h1>

    <b-button @click="callGetReport()">Get report</b-button>

    <div class="bootstrap" style="margin: 20px">
      <b-table striped hover :items="backendResponse"></b-table>
    </div>

  </div>
</template>

<script>
  import api from "./backend-api";

  export default {
    name: 'service',

    data () {
      return {
        backendResponse: [],
        errors: []
      }
    },
    methods: {
      // Fetches posts when the component is created.
      callGetReport () {
        api.getReport().then(response => {
            this.backendResponse = response.data;
            console.log(response.data)
        })
        .catch(error => {
          this.errors.push(error)
        })
      }
    }
  }

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
