<template>
  <v-card>
    <v-card-title>
      Consultation
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addConsultation">Add Consultation</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="consultations"
      :search="search"
      @click:row="editConsultation"
    ></v-data-table>
    <ConsultationDialog
      :opened="dialogVisible"
      :consultation="selectedConsultation"
      :patients="patients"
      :doctors="doctors"
      :isDoctor="false"
      @refresh="refreshList"
    ></ConsultationDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ConsultationDialog from "../components/ConsultationDialog";

export default {
  name: "ConsultationList",
  components: { ConsultationDialog },
  data() {
    return {
      consultations: [],
      search: "",
      patients:[],
      doctors:[],
      headers: [
        {
          text: "Patient",
          align: "start",
          sortable: false,
          value: "patient",
        },
        { text: "Doctor", value: "doctor" },
        { text: "Data", value: "data" },
        { text: "Description", value: "description" },
      ],
      dialogVisible: false,
      selectedConsultation: {},
    };
  },
  methods: {
    editConsultation(consultation) {
      this.selectedConsultation = consultation;
      this.dialogVisible = true;
    },
    addConsultation() {
      this.dialogVisible = true;
    },
    async refreshList() {
     this.dialogVisible = false;
     this.selectedConsultation = {};
     this.consultations = await api.consultations.allConsultations();
     this.doctors = await api.users.allDoctors();
     this.patients = await api.patients.allPatients();


    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
