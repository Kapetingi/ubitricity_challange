import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/`,
  timeout: 1000
});


export default {
    getReport() {
        return AXIOS.get(`/report`);
    },
    parkCar(carId) {
        return AXIOS.post(`/car/park`, {
            car_id: carId
        });
    },
    unparkCar(carId) {
        return AXIOS.post(`/car/unpark`, {
            car_id: carId
        });
    }
}


