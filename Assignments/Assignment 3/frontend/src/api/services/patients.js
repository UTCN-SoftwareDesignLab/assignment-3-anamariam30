import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPatients() {
    return HTTP.get(BASE_URL + "/fo", { headers: authHeader() }).then(
      (response) => { 
        return response.data;
      }
    );
  },
  allPatientsObj() {
    return HTTP.get(BASE_URL + "/fo/obj", { headers: authHeader() }).then(
      (response) => { 
        return response.data;
      }
    );
  },
  create(patient) {
    return HTTP.post(BASE_URL + "/fo", patient, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(patient) {
    return HTTP.patch(BASE_URL + "/fo", patient, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
};
