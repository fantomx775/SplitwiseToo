package pl.edu.agh.utp.api


import dagger.Component
import pl.edu.agh.utp.TransactionsAdapter

// Definition of a Dagger component
@Component
interface AppComponent {
    fun inject(transactionsAdapter: TransactionsAdapter)
}