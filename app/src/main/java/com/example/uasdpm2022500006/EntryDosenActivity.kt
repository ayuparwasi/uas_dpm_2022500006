package com.example.uasdpm2022500006

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EntryDosenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_dosen)

        val modeEdit = intent.hasExtra("nidn") && intent.hasExtra("nama_dosen") && intent.hasExtra("jabatan") && intent.hasExtra("golongan_pangkat") && intent.hasExtra("pendidikan") && intent.hasExtra("keahlian") && intent.hasExtra("program_studi")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etKdDosen = findViewById<EditText>(R.id.etKdDosen)
        val etnmDosen = findViewById<EditText>(R.id.etNmDosen)
        val sptJabatan = findViewById<Spinner>(R.id.sptJabatan)
        val sptGolPang = findViewById<Spinner>(R.id.sptGolPangkat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etKeahlian = findViewById<EditText>(R.id.etKeahlian)
        val etProdi = findViewById<EditText>(R.id.etProdi)
        val btnSimpan = findViewById<Button>(R.id.btntSimpan)

        val jabatan = arrayOf("Tenaga Pengajar", "Asisten Ahli", "Lektor","Lektor Kepala","Guru Besar")
        val golPang = arrayOf(
            "III/a - Penata Muda",
            "III/b - Penata Muda Tingkat I",
            "III/c - Penata",
            "III/d - Penata Tingkat I",
            "IV/a - Pembina",
            "IV/b - Pembina Tingkat I",
            "IV/c - Pembina Utama Muda",
            "IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama"
        )
        val adpJabatan = ArrayAdapter(
            this@EntryDosenActivity,
            android.R.layout.simple_spinner_dropdown_item,
            jabatan
        )

        val adpGolpang = ArrayAdapter(this@EntryDosenActivity,
            android.R.layout.simple_spinner_dropdown_item, golPang)
        sptJabatan.adapter = adpJabatan
        sptGolPang.adapter = adpGolpang

        if(modeEdit){
            val nidn = intent.getStringExtra("nidn")
            val nama_dosen = intent.getStringExtra("nama_dosen")
            val xjabatan = intent.getStringExtra("jabatan")
            val xgolpang = intent.getStringExtra("golongan_pangkat")
            val pendidikan = intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("keahlian")
            val program_studi = intent.getStringExtra("program_studi")

            etKdDosen.setText(nidn)
            etnmDosen.setText(nama_dosen)
            sptJabatan.setSelection(jabatan.indexOf(xjabatan))
            sptGolPang.setSelection(golPang.indexOf(xgolpang))
            if(pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etKeahlian.setText(keahlian)
            etProdi.setText(program_studi)
        }
        etKdDosen.isEnabled = !modeEdit

        btnSimpan.setOnClickListener{
            if("${etKdDosen.text}".isNotEmpty() && "${etnmDosen.text}".isNotEmpty() && (rdS2.isChecked || rdS3.isChecked)){
                val db = DbHelper(this@EntryDosenActivity)
                db.nidn = "${etKdDosen.text}"
                db.nmDosen = "${etnmDosen.text}"
                db.Jabatan = sptJabatan.selectedItem as String
                db.golongan_pangkat = sptGolPang.selectedItem as String
                db.pendidikan = if(rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etKeahlian.text}"
                db.prodi = "${etProdi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etKdDosen.text}")){
                    Toast.makeText(
                        this@EntryDosenActivity,
                        "Data Dosen Berhasil diSimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@EntryDosenActivity,
                        "Data Dosen Gagal diSimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@EntryDosenActivity,
                    "Data Dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}