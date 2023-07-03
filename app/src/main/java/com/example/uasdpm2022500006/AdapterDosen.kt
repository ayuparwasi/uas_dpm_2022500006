package com.example.uasdpm2022500006

import java.security.AccessControlContext
import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdapterDosen(
    private val getContext: Context,
    private val customListItem : ArrayList<Dosen>
): ArrayAdapter<Dosen>(getContext, 0, customListItem){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup) : View {
        var listLayout = convertView
        val holder : ViewHolder
        if(listLayout == null){
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.activity_item, parent, false)
            holder = ViewHolder()
            with(holder){
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvkdDosen = listLayout.findViewById(R.id.tvKdDosen)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.nmDosen)
        holder.tvkdDosen!!.setText(listItem.nidn)
        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntryDosenActivity::class.java)
            i.putExtra("nidn", listItem.nidn)
            i.putExtra("nama_dosen", listItem.nmDosen)
            i.putExtra("jabatan", listItem.Jabatan)
            i.putExtra("golongan_pangkat", listItem.golongan_pangkat)
            i.putExtra("pendidikan", listItem.pendidikan)
            i.putExtra("keahlian", listItem.keahlian)
            i.putExtra("program_studi", listItem.prodi)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener{
            val db = DbHelper(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvkdDosen!!.text
            val nama = holder.tvNmDosen!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                    Apakah Anda yakin akan menghapus data ini ?
                    $nama [$kode]
                """.trimIndent())
                setPositiveButton("Ya"){_,_->
                    if(db.hapus("$kode"))
                        Toast.makeText(context,"Data dosen berhasil dihapus", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context,"Data dosen gagal dihapus", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }
        return listLayout!!
    }
    class ViewHolder{
        internal var tvNmDosen : TextView? = null
        internal var tvkdDosen : TextView? = null
        internal var btnEdit : ImageButton? = null
        internal var btnHapus : ImageButton? = null
    }
}