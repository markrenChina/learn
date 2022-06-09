package demo

class House {

    companion object{
        class Build() {
            private fun buildRoom(){

            }

            fun addRoom()= this.also {
                buildRoom()
            }

            fun addWCRoom()  = this.also {
                buildRoom()
            }

            fun build() : House = House()
        }
    }
}

fun main(){
    House.Companion.Build().addRoom().addWCRoom().build()
}