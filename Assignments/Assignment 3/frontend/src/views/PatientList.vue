<template>
  <v-card>
    <v-card-title>
      Patients
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addPatient">Add Patient</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="patients"
      :search="search"
      @click:row="editPatient"
    ></v-data-table>
    <PatientDialog
      :opened="dialogVisible"
      :patient="selectedPatient"
      @refresh="refreshList"
    ></PatientDialog>
     <router-link to="consultations">
      <v-btn outlined color="blue"> Consultation </v-btn>
    </router-link>
  </v-card>
</template>

<script>
import api from "../api";
import PatientDialog from "../components/PatientDialog";

export default {
  name: "PatientList",
  components: { PatientDialog },
  data() {
    return {
      patients: [],
      search: "",
      headers: [
        {
          text: "name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "idc", value: "idc" },
        { text: "pnc", value: "pnc" },
        { text: "date_of_birth", value: "date_of_birth" },
        { text: "address", value: "address" },

      ],
      dialogVisible: false,
      selectedPatient: {},
    };
  },
  methods: {
    editPatient(patient) {
      this.selectedPatient = patient;
      this.dialogVisible = true;
    },
    addPatient() {
      this.dialogVisible = true;
    },
    async refreshList() {
     this.dialogVisible = false;
     this.selectedPatient = {};
     this.patients = await api.patients.allPatientsObj();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
