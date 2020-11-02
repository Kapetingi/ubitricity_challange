<template>
  <div class="hello">
    <h1>{{ hellomsg }}</h1>
    <b-alert
          :show="errorAlert"
          variant="danger"
        >
        <p> {{error}} </p>
    </b-alert>
    <b-form v-on:submit.prevent="onSubmit" v-on:reset.prevent="onReset">
       <b-form-group
        id="input-group-1"
        label="Car id number"
        label-for="input-1"
        description="Car registration number">
            <b-form-input
              id="input-1"
              v-model="form.car_id"
              required
              placeholder="Enter car number"></b-form-input>
       </b-form-group>
       <b-button type="submit" variant="primary">Park</b-button>
       <b-button type="reset" variant="primary">Unpark</b-button>
     </b-form>
  </div>
</template>

<script>
import api from "./backend-api";

export default {
  name: 'hello',
  props: { hellomsg: { type: String, required: true } },
  data(){
    return{
      form:{
        car_id: ''
      },
      error: '',
      errorAlert: false
    }
  },
  methods: {
        onSubmit () {
          api.parkCar(this.form.car_id).then(response => {
              this.backendResponse = response.data;
              if (this.backendResponse.status == 'failure') {
                this.errorAlert = true;
                this.error = this.backendResponse.description
              } else {
                this.errorAlert = false;
              }

          })
          .catch(error => {
            this.errors.push(error)
          })
        },
        onReset () {
          api.unparkCar(this.form.car_id).then(response => {
              this.backendResponse = response.data;
               if (this.backendResponse.status == 'failure') {
                  this.errorAlert = true;
                  this.error = this.backendResponse.description
              } else {
                this.errorAlert = false;
              }
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

form {
  margin: 10%
}

button {
  margin-left: 10px
}

.alert {
  margin: 10%
}

</style>
