import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allConsultations() {
    return HTTP.get(BASE_URL +"/cons", { headers: authHeader() }).then(
      (response) => { 
        console.log(response.data);
        return response.data;
      }
    );
  },

  allConsultationsByDoctor(id) {
    return HTTP.get(BASE_URL +"/cons/"+id, { headers: authHeader() }).then(
      (response) => { 
        return response.data;
      }
    );
  },
  create(consultation) {
    return HTTP.post(BASE_URL + "/cons", consultation, { headers: authHeader() }).then(
      (response) => {
        return response.data;}
    );
  },

  edit(consultation) {
    return HTTP.patch(BASE_URL + "/cons", consultation, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  remove(id){
    return HTTP.delete(BASE_URL + "/cons/"+id,{ headers: authHeader()});
  },
};
