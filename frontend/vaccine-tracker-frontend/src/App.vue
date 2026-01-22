<template>
  <div class="container">
    <h2>Vaccine Tracker – Immunization Log</h2>

    <!-- Add / Update Form -->
    <form @submit.prevent="addOrUpdateVaccine">
      <input v-model="form.vaccineName" placeholder="Vaccine Name" required />

      <input
        type="number"
        v-model.number="form.doseNumber"
        placeholder="Dose Number"
        required
      />

      <input
        type="number"
        v-model.number="form.totalDoses"
        placeholder="Total Doses"
        required
      />

      <input
        type="number"
        v-model.number="form.gapDays"
        placeholder="Gap (days)"
        required
      />

      <button type="submit">
        {{ editingId ? "Update Vaccine" : "Add Vaccine" }}
      </button>

      <button v-if="editingId" type="button" class="cancel" @click="resetForm">
        Cancel
      </button>
    </form>

    <!-- Vaccine Table -->
    <div class="table-wrapper" v-if="vaccines.length">
      <table>
        <thead>
          <tr>
            <th>Vaccine</th>
            <th>Dose</th>
            <th>Date Taken</th>
            <th>Next Due</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="v in vaccines" :key="v.id">
            <td>{{ v.vaccineName }}</td>
            <td>{{ v.doseNumber }}</td>
            <td>{{ v.dateTaken }}</td>
            <td>{{ v.nextDueDate || "-" }}</td>
            <td>
              <span :class="v.status.toLowerCase()">
                {{ v.status }}
              </span>
            </td>
            <td>
              <button @click="editVaccine(v)">Edit</button>
              <button class="delete" @click="deleteVaccine(v.id)">
                Delete
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <p v-else>No records found</p>
  </div>
</template>

<script>
import api from "./services/api";

export default {
  data() {
    return {
      vaccines: [],
      editingId: null,
      form: {
        vaccineName: "",
        doseNumber: "",
        totalDoses: "",
        gapDays: "",
      },
    };
  },

  methods: {
    async loadVaccines() {
      const res = await api.get("/vaccines");
      this.vaccines = res.data;
    },

    async addOrUpdateVaccine() {
      if (this.editingId) {
        await api.put(`/vaccines/${this.editingId}`, this.form);
      } else {
        await api.post("/vaccines", this.form);
      }

      this.resetForm();
      this.loadVaccines();
    },

    editVaccine(vaccine) {
      this.editingId = vaccine.id;
      this.form = {
        vaccineName: vaccine.vaccineName,
        doseNumber: vaccine.doseNumber,
        totalDoses: vaccine.totalDoses,
        gapDays: vaccine.gapDays,
      };
    },

    async deleteVaccine(id) {
      if (confirm("Are you sure you want to delete this record?")) {
        await api.delete(`/vaccines/${id}`);
        this.loadVaccines();
      }
    },

    resetForm() {
      this.editingId = null;
      this.form = {
        vaccineName: "",
        doseNumber: "",
        totalDoses: "",
        gapDays: "",
      };
    },
  },

  mounted() {
    this.loadVaccines();
  },
};
</script>

<style>
/* Page container */
.container {
  max-width: 1000px;
  margin: 40px auto;
  padding: 24px;
  font-family:
    "Inter",
    system-ui,
    -apple-system,
    BlinkMacSystemFont,
    sans-serif;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
}

/* Title */
h2 {
  margin-bottom: 24px;
  font-size: 24px;
  font-weight: 700;
  color: #1f2937;
}

/* Form */
form {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 14px;
  margin-bottom: 28px;
}

/* Inputs */
input {
  padding: 10px 12px;
  border-radius: 8px;
  border: 1px solid #d1d5db;
  font-size: 14px;
  transition:
    border-color 0.2s,
    box-shadow 0.2s;
}

input:focus {
  outline: none;
  border-color: #2563eb;
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
}

/* Buttons */
button {
  padding: 10px 16px;
  border-radius: 8px;
  border: none;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition:
    background-color 0.2s,
    transform 0.1s;
}

/* Primary button */
form button[type="submit"] {
  background-color: #2563eb;
  color: white;
}

form button[type="submit"]:hover {
  background-color: #1e4fd8;
  transform: translateY(-1px);
}

/* Cancel button */
button.cancel {
  background-color: #9ca3af;
  color: white;
}

button.cancel:hover {
  background-color: #6b7280;
}

/* Delete button */
button.delete {
  background-color: #dc2626;
  color: white;
  margin-left: 6px;
}

button.delete:hover {
  background-color: #b91c1c;
}

.table-wrapper {
  width: 100%;
}

/* Table */
table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  background: #ffffff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.06);
}

/* Table header */
thead {
  background-color: #f3f4f6;
}

th {
  padding: 12px;
  font-size: 14px;
  text-align: left;
  font-weight: 600;
  color: #374151;
}

/* Table body */
td {
  padding: 12px;
  font-size: 14px;
  color: #1f2937;
  border-top: 1px solid #e5e7eb;
}

/* Row hover */
tbody tr:hover {
  background-color: #f9fafb;
}

/* Status badges */
.due,
.overdue,
.completed {
  display: inline-block;
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 600;
}

/* Status colors */
.due {
  background-color: #fef3c7;
  color: #92400e;
}

.overdue {
  background-color: #fee2e2;
  color: #991b1b;
}

.completed {
  background-color: #dcfce7;
  color: #166534;
}

/* Empty state */
p {
  margin-top: 20px;
  color: #6b7280;
  font-size: 14px;
}

/* Responsive */
@media (max-width: 640px) {
  h2 {
    font-size: 20px;
  }

  th,
  td {
    font-size: 13px;
  }

  button {
    width: 100%;
  }
}
@media (max-width: 450px) {
  .table-wrapper {
    overflow-x: auto;
    -webkit-overflow-scrolling: touch;
  }

  table {
    min-width: 700px;
  }
}
</style>
