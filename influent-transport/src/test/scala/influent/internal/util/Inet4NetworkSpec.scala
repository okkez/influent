package influent.internal.util

import java.net.InetAddress

import org.scalatest.Matchers._
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.WordSpec

class Inet4NetworkSpec extends WordSpec {
  "contains" should {
    val data1 = Table(
      ("address", "isContained"),
      ("192.168.1.1", true),
      ("192.168.1.254", true),
      ("192.168.2.1", false)
    )
    forAll(data1) { (address, isContained) =>
      s"return $isContained" when {
        s"$address is given" in {
          val network = InetNetwork.getBySpec("192.168.1.0/24")
          assert(network.contains(InetAddress.getByName(address)) === isContained)
        }
      }
    }
    val data2 = Table(
      ("address", "isContained"),
      ("10.1.10.1", true),
      ("10.1.20.1", true),
      ("10.1.31.254", true),
      ("10.1.40.1", false),
      ("10.1.50.1", false)
    )
    forAll(data2) { (address, isContained) =>
      s"return $isContained" when {
        s"$address is given" in {
          val network = InetNetwork.getBySpec("10.1.0.0/19")
          assert(network.contains(InetAddress.getByName(address)) === isContained)
        }
      }
    }

    "return false" when {
      "IPv6 address is given" in {
        val network = InetNetwork.getBySpec("10.1.0.0/19")
        assert(network.contains(InetAddress.getByName("::10.1.10.1")) === false)
      }
    }
  }
}
